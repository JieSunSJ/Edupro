<template>
  <div class="page-container">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="avatar-card">
          <div class="avatar-section">
            <el-avatar :size="100" :src="profileForm.image" class="profile-avatar">
              <el-icon :size="48"><User /></el-icon>
            </el-avatar>
            <h3 class="profile-name">{{ profileForm.name }}</h3>
            <p class="profile-no">{{ profileForm.no }}</p>
            <el-tag>{{ profileForm.gender === 1 ? '男' : '女' }}</el-tag>
            <el-tag type="info" style="margin-left:8px">{{ degreeMap[profileForm.degree] }}</el-tag>
            <div class="upload-section">
              <el-upload
                :action="uploadAction"
                :headers="uploadHeaders"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :on-error="handleAvatarError"
                :before-upload="beforeAvatarUpload"
                accept=".jpg,.jpeg,.png,.gif"
              >
                <el-button type="primary" :loading="uploadLoading" size="small">{{ uploadLoading ? '上传中...' : '更换头像' }}</el-button>
              </el-upload>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="16">
        <el-card>
          <template #header>
            <div class="card-header"><span>基本信息</span></div>
          </template>
          <el-form label-width="90px" v-loading="loading">
            <el-form-item label="姓名">
              <el-input :model-value="profileForm.name" disabled />
            </el-form-item>
            <el-form-item label="学号">
              <el-input :model-value="profileForm.no" disabled />
            </el-form-item>
            <el-form-item label="性别">
              <el-input :model-value="profileForm.gender === 1 ? '男' : '女'" disabled />
            </el-form-item>
            <el-form-item label="身份证号">
              <el-input :model-value="profileForm.idCard" disabled />
            </el-form-item>
            <el-form-item label="学历">
              <el-input :model-value="degreeMap[profileForm.degree]" disabled />
            </el-form-item>
            <el-form-item label="毕业时间">
              <el-input :model-value="profileForm.graduationDate" disabled />
            </el-form-item>
            <el-form-item label="班级">
              <el-input :model-value="profileForm.clazzName || '暂未选课'" disabled />
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="profileForm.phone" placeholder="请输入手机号" />
            </el-form-item>
            <el-form-item label="地址">
              <el-input v-model="profileForm.address" placeholder="请输入地址" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" :loading="saveLoading" @click="handleSave">保存信息</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { User } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { getStudentProfile, updateStudentProfile } from '../../api/student'

const loading = ref(false)
const saveLoading = ref(false)
const uploadLoading = ref(false)

const degreeMap = { 1: '初中', 2: '高中', 3: '大专', 4: '本科', 5: '硕士', 6: '博士' }

const profileForm = reactive({
  id: null,
  name: '',
  no: '',
  gender: null,
  idCard: '',
  degree: null,
  graduationDate: '',
  clazzName: '',
  phone: '',
  address: '',
  image: ''
})

const uploadAction = '/api/upload'
const uploadHeaders = { token: localStorage.getItem('token') }

const fetchProfile = async () => {
  loading.value = true
  try {
    const res = await getStudentProfile()
    Object.assign(profileForm, res.data)
  } catch { /* ignore */ }
  loading.value = false
}

const handleAvatarSuccess = (res) => {
  uploadLoading.value = false
  if (res.code === 200) {
    profileForm.image = res.data
    ElMessage.success('头像上传成功')
    const userInfo = JSON.parse(localStorage.getItem('studentInfo') || '{}')
    userInfo.image = res.data
    localStorage.setItem('studentInfo', JSON.stringify(userInfo))
    window.dispatchEvent(new CustomEvent('studentAvatarUpdate', { detail: { image: res.data } }))
  } else {
    ElMessage.error(res.msg || '上传失败')
  }
}

const handleAvatarError = () => {
  uploadLoading.value = false
  ElMessage.error('上传失败，请检查网络')
}

const beforeAvatarUpload = (file) => {
  const isImage = file.type.startsWith('image/')
  const isLt2M = file.size / 1024 / 1024 < 2
  if (!isImage) { ElMessage.error('只能上传图片文件！'); return false }
  if (!isLt2M) { ElMessage.error('图片大小不能超过 2MB！'); return false }
  uploadLoading.value = true
  return true
}

const handleSave = async () => {
  saveLoading.value = true
  try {
    await updateStudentProfile(profileForm)
    ElMessage.success('个人信息修改成功')
    const userInfo = JSON.parse(localStorage.getItem('studentInfo') || '{}')
    userInfo.name = profileForm.name
    userInfo.image = profileForm.image
    localStorage.setItem('studentInfo', JSON.stringify(userInfo))
    window.dispatchEvent(new CustomEvent('studentAvatarUpdate', { detail: { image: profileForm.image } }))
  } catch {
    // 错误已在拦截器处理
  } finally {
    saveLoading.value = false
  }
}

onMounted(() => {
  fetchProfile()
})
</script>

<style scoped>
.page-container {
  max-width: 1000px;
}
.avatar-card {
  text-align: center;
}
.avatar-section {
  padding: 20px 0;
}
.profile-avatar {
  margin-bottom: 16px;
}
.profile-name {
  margin: 0 0 8px 0;
  font-size: 20px;
  color: #303133;
}
.profile-no {
  margin: 0 0 12px 0;
  font-size: 14px;
  color: #909399;
}
.upload-section {
  margin-top: 16px;
}
</style>
