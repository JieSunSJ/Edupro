<template>
  <div class="page-container">
    <el-card class="search-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="班级名称">
          <el-input v-model="searchForm.name" placeholder="请输入班级名称" clearable />
        </el-form-item>
        <el-form-item label="开课时间">
          <el-date-picker v-model="searchForm.begin" type="date" placeholder="开始日期" value-format="YYYY-M-D" />
        </el-form-item>
        <el-form-item label="结课时间">
          <el-date-picker v-model="searchForm.end" type="date" placeholder="结束日期" value-format="YYYY-M-D" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="fetchData">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <span>班级列表</span>
          <el-button type="primary" @click="handleAdd">新增班级</el-button>
        </div>
      </template>

      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="name" label="班级名称" width="175" />
        <el-table-column prop="room" label="教室" width="135" />
        <el-table-column prop="masterName" label="班主任" width="115" />
        <el-table-column prop="subject" label="学科" width="95">
          <template #default="{ row }">{{ subjectMap[row.subject] }}</template>
        </el-table-column>
        <el-table-column prop="beginDate" label="开课时间" width="135" />
        <el-table-column prop="endDate" label="结课时间" width="135" />
        <el-table-column prop="status" label="状态" width="105">
          <template #default="{ row }">
            <el-tag :type="statusTagType[row.status]">{{ statusMap[row.status] }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="200" />
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

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="班级名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入班级名称" />
        </el-form-item>
        <el-form-item label="教室" prop="room">
          <el-input v-model="form.room" placeholder="请输入教室" />
        </el-form-item>
        <el-form-item label="开课时间" prop="beginDate">
          <el-date-picker v-model="form.beginDate" type="date" placeholder="选择日期" style="width:100%" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="结课时间" prop="endDate">
          <el-date-picker v-model="form.endDate" type="date" placeholder="选择日期" style="width:100%" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="学科" prop="subject">
          <el-select v-model="form.subject" placeholder="请选择学科" style="width:100%">
            <el-option v-for="(v, k) in subjectMap" :key="k" :label="v" :value="Number(k)" />
          </el-select>
        </el-form-item>
        <el-form-item label="班主任" prop="masterId">
          <el-select v-model="form.masterId" placeholder="请选择班主任" style="width:100%" filterable>
            <el-option v-for="e in empList" :key="e.id" :label="`${e.name} (${e.username})`" :value="e.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态" style="width:100%">
            <el-option v-for="(v, k) in statusMap" :key="k" :label="v" :value="Number(k)" />
          </el-select>
        </el-form-item>
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
import { getClazzPage, addClazz, updateClazz, deleteClazz, getClazzById } from '../../api/clazz'
import { getEmpList } from '../../api/emp'

const subjectMap = { 1: 'Java', 2: '前端', 3: 'Python', 4: '大数据', 5: 'AI' }
const statusMap = { 0: '未开课', 1: '已开课', 2: '已完结' }
const statusTagType = { 0: 'info', 1: 'success', 2: 'danger' }
const empList = ref([])

const searchForm = reactive({ name: '', begin: '', end: '' })
const tableData = ref([])
const loading = ref(false)
const page = ref(1)
const pageSize = ref(5)
const total = ref(0)

const dialogVisible = ref(false)
const dialogTitle = ref('新增班级')
const isEdit = ref(false)
const submitLoading = ref(false)
const formRef = ref(null)

const form = reactive({
  id: null, name: '', room: '', beginDate: '', endDate: '', subject: 1, masterId: 1, status: 0
})

const rules = {
  name: [{ required: true, message: '请输入班级名称', trigger: 'blur' }],
  room: [{ required: true, message: '请输入教室', trigger: 'blur' }],
  beginDate: [{ required: true, message: '请选择开课时间', trigger: 'change' }],
  endDate: [{ required: true, message: '请选择结课时间', trigger: 'change' }],
  subject: [{ required: true, message: '请选择学科', trigger: 'change' }]
}

const fetchData = async () => {
  loading.value = true
  try {
    const params = { page: page.value, pageSize: pageSize.value }
    if (searchForm.name) params.name = searchForm.name
    if (searchForm.begin) params.begin = searchForm.begin
    if (searchForm.end) params.end = searchForm.end
    const res = await getClazzPage(params)
    tableData.value = res.data.rows
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const resetSearch = () => {
  searchForm.name = ''
  searchForm.begin = ''
  searchForm.end = ''
  fetchData()
}

const handleAdd = () => {
  isEdit.value = false
  dialogTitle.value = '新增班级'
  Object.assign(form, { id: null, name: '', room: '', beginDate: '', endDate: '', subject: 1, masterId: 1, status: 0 })
  dialogVisible.value = true
}

const handleEdit = async (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑班级'
  const res = await getClazzById(row.id)
  Object.assign(form, res.data)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  submitLoading.value = true
  try {
    if (isEdit.value) {
      await updateClazz({ ...form })
      ElMessage.success('修改成功')
    } else {
      await addClazz({ ...form })
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    fetchData()
  } finally {
    submitLoading.value = false
  }
}

const handleDelete = async (id) => {
  await ElMessageBox.confirm('确定要删除该班级吗？', '提示', { type: 'warning' })
  try {
    await deleteClazz(id)
    ElMessage.success('删除成功')
    fetchData()
  } catch { /* 取消 */ }
}

onMounted(async () => {
  const res = await getEmpList()
  empList.value = res.data || []
  fetchData()
})
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
.search-card :deep(.el-card__body) { padding-bottom: 0; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.pagination { margin-top: 16px; display: flex; justify-content: flex-end; }
</style>