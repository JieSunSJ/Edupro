<template>
  <div class="page-container">
    <!-- 搜索区域 -->
    <el-card class="search-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="姓名">
          <el-input v-model="searchForm.name" placeholder="请输入姓名" clearable style="width:120px" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="searchForm.gender" placeholder="全部" clearable style="width:100px">
            <el-option label="男" :value="1" />
            <el-option label="女" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="职位">
          <el-select v-model="searchForm.job" placeholder="全部" clearable style="width:110px">
            <el-option v-for="(v, k) in jobMap" :key="k" :label="v" :value="Number(k)" />
          </el-select>
        </el-form-item>
        <el-form-item label="部门">
          <el-select v-model="searchForm.deptId" placeholder="全部" clearable style="width:110px">
            <el-option v-for="d in deptList" :key="d.id" :label="d.name" :value="d.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="薪资">
          <el-input-number v-model="searchForm.salaryMin" placeholder="最低" :min="0" :precision="2" :step="100" controls-position="right" size="small" style="width:120px" />
          <span style="margin:0 5px;color:#999">-</span>
          <el-input-number v-model="searchForm.salaryMax" placeholder="最高" :min="0" :precision="2" :step="100" controls-position="right" size="small" style="width:120px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="fetchData">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 表格区域 -->
    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <span>员工列表</span>
          <div>
            <el-button type="primary" @click="handleAdd">新增员工</el-button>
            <el-button type="danger" :disabled="!selectedIds.length" @click="handleBatchDelete">批量删除</el-button>
          </div>
        </div>
      </template>

      <el-table :data="tableData" border stripe v-loading="loading" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="50" />
        <el-table-column prop="name" label="姓名" width="115" />
        <el-table-column prop="username" label="用户名" width="130" />
        <el-table-column prop="gender" label="性别" width="70">
          <template #default="{ row }">{{ row.gender === 1 ? '男' : '女' }}</template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="job" label="职位" width="100">
          <template #default="{ row }">{{ jobMap[row.job] || '-' }}</template>
        </el-table-column>
        <el-table-column prop="salary" label="薪资" width="90">
          <template #default="{ row }">{{ row.salary ? Number(row.salary).toLocaleString() : '-' }}</template>
        </el-table-column>
        <el-table-column prop="deptName" label="部门" width="125" />
        <el-table-column prop="entryDate" label="入职日期" width="135" />
        <el-table-column label="头像" width="95">
          <template #default="{ row }">
            <el-image v-if="row.image" :src="row.image" style="width:36px;height:36px;border-radius:4px" fit="cover" :preview-src-list="[row.image]" preview-teleported />
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="155" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" text size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" text size="small" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="page"
        v-model:page-size="pageSize"
        :page-sizes="[5, 10, 20, 50]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        class="pagination"
        @size-change="fetchData"
        @current-change="fetchData"
      />
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="780px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username" placeholder="请输入用户名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="form.gender" placeholder="请选择性别" style="width:100%">
                <el-option label="男" :value="1" />
                <el-option label="女" :value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="职位" prop="job">
              <el-select v-model="form.job" placeholder="请选择职位" style="width:100%">
                <el-option v-for="(v, k) in jobMap" :key="k" :label="v" :value="Number(k)" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="薪资" prop="salary">
              <el-input-number v-model="form.salary" :min="0" :precision="0" :step="1000" :controls="true" style="width:100%" placeholder="请输入薪资" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入职日期" prop="entryDate">
              <el-date-picker v-model="form.entryDate" type="date" placeholder="选择日期" style="width:100%" value-format="YYYY-MM-DD" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部门" prop="deptId">
              <el-select v-model="form.deptId" placeholder="请选择部门" style="width:100%">
                <el-option v-for="d in deptList" :key="d.id" :label="d.name" :value="d.id" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="头像">
              <el-upload
                :action="uploadAction"
                :headers="uploadHeaders"
                :show-file-list="false"
                :on-success="handleUploadSuccess"
                :on-error="handleUploadError"
                :before-upload="beforeUpload"
                accept=".jpg,.jpeg,.png,.gif"
              >
                <el-button type="primary" :loading="uploadLoading">{{ uploadLoading ? '上传中...' : '上传头像' }}</el-button>
              </el-upload>
              <div v-if="form.image" style="margin-top:8px">
                <el-image :src="form.image" style="width:60px;height:60px;border-radius:4px" fit="cover" :preview-src-list="[form.image]" preview-teleported />
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">工作经历</el-divider>
        <el-table :data="form.exprList" border style="width:100%">
          <el-table-column label="公司名称" width="160">
            <template #default="{ row, $index }">
              <el-input v-model="row.company" placeholder="请输入" size="small" />
            </template>
          </el-table-column>
          <el-table-column label="担任职位" width="130">
            <template #default="{ row }">
              <el-input v-model="row.job" placeholder="请输入" size="small" />
            </template>
          </el-table-column>
          <el-table-column label="开始时间" width="170">
            <template #default="{ row }">
              <el-date-picker v-model="row.begin" type="date" placeholder="选择日期" style="width:100%" value-format="YYYY-MM-DD" size="small" />
            </template>
          </el-table-column>
          <el-table-column label="结束时间" width="170">
            <template #default="{ row }">
              <el-date-picker v-model="row.end" type="date" placeholder="选择日期" style="width:100%" value-format="YYYY-MM-DD" size="small" />
            </template>
          </el-table-column>
          <el-table-column label="操作" width="70" align="center">
            <template #default="{ $index }">
              <el-button type="danger" :icon="Delete" circle size="small" @click="removeExpr($index)" />
            </template>
          </el-table-column>
        </el-table>
        <el-button type="primary" plain size="small" @click="addExpr" style="margin-top:10px">+ 添加工作经历</el-button>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Delete } from '@element-plus/icons-vue'
import { getEmpPage, addEmp, updateEmp, deleteEmp, getEmpById, getDeptList } from '../../api/emp'

const jobMap = { 1: '班主任', 2: '讲师', 3: '学工主管', 4: '教研主管', 5: '咨询师' }

const searchForm = reactive({ name: '', gender: null, job: null, deptId: null, salaryMin: null, salaryMax: null })
const tableData = ref([])
const loading = ref(false)
const page = ref(1)
const pageSize = ref(10)
const total = ref(0)
const selectedIds = ref([])
const deptList = ref([])

const dialogVisible = ref(false)
const dialogTitle = ref('新增员工')
const isEdit = ref(false)
const submitLoading = ref(false)
const uploadLoading = ref(false)
const formRef = ref(null)

const form = reactive({
  id: null, username: '', name: '', password: '123456', gender: 1,
  phone: '', job: 1, salary: 0, entryDate: '', image: '', deptId: 1,
  exprList: []
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
  job: [{ required: true, message: '请选择职位', trigger: 'change' }]
}

const uploadAction = '/api/upload'
const uploadHeaders = { token: localStorage.getItem('token') }

const fetchData = async () => {
  loading.value = true
  try {
    const params = { page: page.value, pageSize: pageSize.value }
    if (searchForm.name) params.name = searchForm.name
    if (searchForm.gender != null) params.gender = searchForm.gender
    if (searchForm.job != null) params.job = searchForm.job
    if (searchForm.deptId != null) params.deptId = searchForm.deptId
    if (searchForm.salaryMin != null) params.salaryMin = searchForm.salaryMin
    if (searchForm.salaryMax != null) params.salaryMax = searchForm.salaryMax
    const res = await getEmpPage(params)
    tableData.value = res.data.rows
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const resetSearch = () => {
  searchForm.name = ''
  searchForm.gender = null
  searchForm.job = null
  searchForm.deptId = null
  searchForm.salaryMin = null
  searchForm.salaryMax = null
  fetchData()
}

const handleSelectionChange = (rows) => {
  selectedIds.value = rows.map(r => r.id)
}

const handleAdd = () => {
  isEdit.value = false
  dialogTitle.value = '新增员工'
  Object.assign(form, { id: null, username: '', name: '', password: '123456', gender: 1, phone: '', job: 1, salary: 0, entryDate: '', image: '', deptId: 1, exprList: [] })
  dialogVisible.value = true
}

const handleEdit = async (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑员工'
  const res = await getEmpById(row.id)
  const data = res.data
  Object.assign(form, {
    ...data,
    salary: data.salary ? Number(data.salary) : 0,
    exprList: (data.exprList || []).map(e => ({
      id: e.id,
      empId: e.empId,
      company: e.company || '',
      job: e.job || '',
      begin: e.begin || '',
      end: e.end || ''
    }))
  })
  dialogVisible.value = true
}

const handleSubmit = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  submitLoading.value = true
  try {
    if (isEdit.value) {
      await updateEmp({ ...form })
      ElMessage.success('修改成功')
      const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
      if (userInfo.id === form.id) {
        userInfo.image = form.image
        localStorage.setItem('userInfo', JSON.stringify(userInfo))
        window.dispatchEvent(new CustomEvent('avatarUpdate', { detail: { image: form.image } }))
      }
    } else {
      await addEmp({ ...form })
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    fetchData()
  } finally {
    submitLoading.value = false
  }
}

const handleDelete = async (id) => {
  await ElMessageBox.confirm('确定要删除该员工吗？', '提示', { type: 'warning' })
  try {
    await deleteEmp([id])
    ElMessage.success('删除成功')
    fetchData()
  } catch {}
}

const handleBatchDelete = async () => {
  await ElMessageBox.confirm(`确定要删除选中的 ${selectedIds.value.length} 条数据吗？`, '提示', { type: 'warning' })
  try {
    await deleteEmp(selectedIds.value)
    ElMessage.success('删除成功')
    fetchData()
  } catch {}
}

const handleUploadSuccess = (res) => {
  uploadLoading.value = false
  if (res.code === 200) {
    form.image = res.data
    ElMessage.success('头像上传成功')
  } else {
    ElMessage.error(res.msg || '上传失败，请重试')
  }
}

const handleUploadError = () => {
  uploadLoading.value = false
  ElMessage.error('上传失败，请检查网络连接或联系管理员')
}

const beforeUpload = (file) => {
  const isImage = file.type.startsWith('image/')
  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isImage) {
    ElMessage.error('只能上传图片文件！')
    return false
  }
  if (!isLt2M) {
    ElMessage.error('图片大小不能超过 2MB！')
    return false
  }

  uploadLoading.value = true
  return true
}

const addExpr = () => {
  form.exprList.push({ company: '', job: '', begin: '', end: '' })
}

const removeExpr = (index) => {
  form.exprList.splice(index, 1)
}

onMounted(async () => {
  const res = await getDeptList()
  deptList.value = res.data
  fetchData()
})
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
.search-card :deep(.el-card__body) { padding-bottom: 0; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.pagination { margin-top: 16px; display: flex; justify-content: flex-end; }
</style>