<template>
  <el-container class="layout-container">
    <el-aside :width="isCollapse ? '64px' : '200px'" class="layout-aside">
      <div class="logo">
        <span v-show="!isCollapse">Tlias 管理系统</span>
        <span v-show="isCollapse">T</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :collapse-transition="false"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
        router
      >
        <el-menu-item index="/student">
          <el-icon><School /></el-icon>
          <span>学生管理</span>
        </el-menu-item>
        <el-menu-item index="/clazz">
          <el-icon><OfficeBuilding /></el-icon>
          <span>班级管理</span>
        </el-menu-item>
        <el-menu-item index="/report">
          <el-icon><DataAnalysis /></el-icon>
          <span>数据统计</span>
        </el-menu-item>
        <el-menu-item index="/log">
          <el-icon><Document /></el-icon>
          <span>操作日志</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="layout-header">
        <div class="header-left">
          <el-icon class="collapse-btn" @click="isCollapse = !isCollapse">
            <Fold v-if="!isCollapse" />
            <Expand v-else />
          </el-icon>
        </div>
        <div class="header-right">
          <el-dropdown trigger="click" @command="handleCommand">
            <span class="user-dropdown">
              <el-avatar :size="32" :src="userImage" class="user-avatar">
                <el-icon :size="18"><User /></el-icon>
              </el-avatar>
              <span class="user-name">{{ userName }}</span>
              <el-icon class="dropdown-arrow"><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">
                  <el-icon><User /></el-icon> 个人信息
                </el-dropdown-item>
                <el-dropdown-item command="password">
                  <el-icon><Lock /></el-icon> 修改密码
                </el-dropdown-item>
                <el-dropdown-item divided command="logout">
                  <el-icon><SwitchButton /></el-icon> 退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <el-main class="layout-main">
        <router-view />
      </el-main>
    </el-container>

    <!-- 个人信息对话框 -->
    <el-dialog v-model="profileVisible" title="个人信息" width="450px" :close-on-click-modal="false">
      <el-form label-width="80px" v-loading="profileLoading">
        <el-form-item label="用户名">
          <el-input :model-value="profileForm.username" disabled />
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="profileForm.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="角色">
          <el-tag :type="userRole === 1 ? 'danger' : 'info'">{{ userRole === 1 ? '管理员' : '普通用户' }}</el-tag>
        </el-form-item>
        <el-form-item label="头像">
          <el-upload
            :action="uploadAction"
            :headers="uploadHeaders"
            :show-file-list="false"
            :on-success="handleProfileUploadSuccess"
            :on-error="handleProfileUploadError"
            :before-upload="beforeProfileUpload"
            accept=".jpg,.jpeg,.png,.gif"
          >
            <el-button type="primary" :loading="profileUploadLoading" size="small">{{ profileUploadLoading ? '上传中...' : '上传头像' }}</el-button>
          </el-upload>
          <div v-if="profileForm.image" style="margin-top:8px">
            <el-image :src="profileForm.image" style="width:60px;height:60px;border-radius:4px" fit="cover" :preview-src-list="[profileForm.image]" preview-teleported />
          </div>
          <span v-else style="color:#999;font-size:13px">未设置头像</span>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="profileVisible = false">取消</el-button>
        <el-button type="primary" :loading="profileSaveLoading" @click="handleSaveProfile">保存</el-button>
      </template>
    </el-dialog>

    <!-- 修改密码对话框 -->
    <el-dialog v-model="passwordVisible" title="修改密码" width="450px" :close-on-click-modal="false">
      <el-form ref="passwordFormRef" :model="passwordForm" :rules="passwordRules" label-width="90px">
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="passwordForm.oldPassword" type="password" placeholder="请输入原密码" show-password />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="passwordForm.newPassword" type="password" placeholder="请输入新密码" show-password />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="passwordForm.confirmPassword" type="password" placeholder="请再次输入新密码" show-password />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="passwordVisible = false">取消</el-button>
        <el-button type="primary" :loading="passwordLoading" @click="handleChangePassword">确定</el-button>
      </template>
    </el-dialog>
  </el-container>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { User, School, OfficeBuilding, DataAnalysis, Document, Fold, Expand, ArrowDown, Lock, SwitchButton } from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { changePassword, getProfile, updateProfile, updateImage } from '../../api/user'

const router = useRouter()
const route = useRoute()
const isCollapse = ref(false)

const activeMenu = computed(() => '/' + route.path.split('/')[1])

const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
const userName = ref(userInfo.name || '管理员')
const userRole = userInfo.role || 1
const userImage = ref(userInfo.image || '')

const handleAvatarUpdate = (e) => { userImage.value = e.detail.image }

onMounted(() => { window.addEventListener('avatarUpdate', handleAvatarUpdate) })
onUnmounted(() => { window.removeEventListener('avatarUpdate', handleAvatarUpdate) })

const handleCommand = async (cmd) => {
  if (cmd === 'profile') {
    profileLoading.value = true
    profileVisible.value = true
    try {
      const res = await getProfile()
      const data = res.data
      profileForm.value = { id: data.id, username: data.username, name: data.name, image: data.image, phone: data.phone, gender: data.gender }
    } catch { /* ignore */ }
    profileLoading.value = false
  } else if (cmd === 'password') {
    passwordForm.value = { oldPassword: '', newPassword: '', confirmPassword: '' }
    passwordVisible.value = true
  } else if (cmd === 'logout') {
    handleLogout()
  }
}

const profileVisible = ref(false)
const profileLoading = ref(false)
const profileSaveLoading = ref(false)
const profileUploadLoading = ref(false)
const profileForm = ref({ id: null, username: '', name: '', image: '' })
const uploadAction = '/api/upload'
const uploadHeaders = { token: localStorage.getItem('token') }

const handleProfileUploadSuccess = async (res) => {
  profileUploadLoading.value = false
  if (res.code === 200) {
    profileForm.value.image = res.data
    try {
      await updateImage({ image: res.data })
      userInfo.image = res.data
      localStorage.setItem('userInfo', JSON.stringify(userInfo))
      userImage.value = res.data
      window.dispatchEvent(new CustomEvent('avatarUpdate', { detail: { image: res.data } }))
      ElMessage.success('头像上传成功')
    } catch {
      ElMessage.error('头像保存失败')
    }
  } else {
    ElMessage.error(res.msg || '上传失败')
  }
}

const handleProfileUploadError = () => {
  profileUploadLoading.value = false
  ElMessage.error('上传失败，请检查网络')
}

const beforeProfileUpload = (file) => {
  const isImage = file.type.startsWith('image/')
  const isLt2M = file.size / 1024 / 1024 < 2
  if (!isImage) { ElMessage.error('只能上传图片文件！'); return false }
  if (!isLt2M) { ElMessage.error('图片大小不能超过 2MB！'); return false }
  profileUploadLoading.value = true
  return true
}

const handleSaveProfile = async () => {
  if (!profileForm.value.name) { ElMessage.warning('请输入姓名'); return }
  profileSaveLoading.value = true
  try {
    await updateProfile(profileForm.value)
    ElMessage.success('个人信息修改成功')
    profileVisible.value = false
    userInfo.name = profileForm.value.name
    userInfo.image = profileForm.value.image
    localStorage.setItem('userInfo', JSON.stringify(userInfo))
    userName.value = profileForm.value.name
    userImage.value = profileForm.value.image
    window.dispatchEvent(new CustomEvent('avatarUpdate', { detail: { image: profileForm.value.image } }))
  } catch {
    // 错误已在拦截器处理
  } finally {
    profileSaveLoading.value = false
  }
}

const passwordVisible = ref(false)
const passwordLoading = ref(false)
const passwordFormRef = ref(null)
const passwordForm = ref({ oldPassword: '', newPassword: '', confirmPassword: '' })

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== passwordForm.value.newPassword) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const passwordRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [{ required: true, min: 6, message: '新密码至少6位', trigger: 'blur' }],
  confirmPassword: [
    { required: true, message: '请再次输入新密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const handleChangePassword = async () => {
  const valid = await passwordFormRef.value.validate().catch(() => false)
  if (!valid) return
  passwordLoading.value = true
  try {
    await changePassword({
      id: userInfo.id,
      oldPassword: passwordForm.value.oldPassword,
      newPassword: passwordForm.value.newPassword
    })
    ElMessage.success('密码修改成功，请重新登录')
    passwordVisible.value = false
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
    router.push('/login')
  } catch {
    // 错误已在拦截器处理
  } finally {
    passwordLoading.value = false
  }
}

const handleLogout = async () => {
  await ElMessageBox.confirm('确定要退出登录吗？', '提示', { type: 'warning' })
  localStorage.removeItem('token')
  localStorage.removeItem('userInfo')
  router.push('/login')
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}
.layout-aside {
  background-color: #304156;
  transition: width 0.3s;
  overflow: hidden;
}
.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}
.layout-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #fff;
  border-bottom: 1px solid #e6e6e6;
  padding: 0 20px;
}
.collapse-btn {
  font-size: 20px;
  cursor: pointer;
}
.header-right {
  display: flex;
  align-items: center;
}
.user-dropdown {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  padding: 4px 8px;
  border-radius: 6px;
  transition: background 0.2s;
}
.user-dropdown:hover {
  background: #f0f2f5;
}
.user-name {
  color: #303133;
  font-size: 14px;
  font-weight: 500;
}
.user-avatar {
  flex-shrink: 0;
}
.dropdown-arrow {
  font-size: 12px;
  color: #909399;
}
.layout-main {
  background: #f0f2f5;
  padding: 20px;
}
</style>