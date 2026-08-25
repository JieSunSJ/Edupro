<template>
  <div class="page-container">
    <el-card class="search-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="姓名">
          <el-input v-model="searchForm.name" placeholder="请输入姓名" clearable />
        </el-form-item>
        <el-form-item label="学历">
          <el-select v-model="searchForm.degree" placeholder="请选择学历" clearable>
            <el-option v-for="(v, k) in degreeMap" :key="k" :label="v" :value="Number(k)" />
          </el-select>
        </el-form-item>
        <el-form-item label="班级">
          <el-select v-model="searchForm.clazzId" placeholder="请选择班级" clearable>
            <el-option v-for="c in clazzList" :key="c.id" :label="c.name" :value="c.id" />
          </el-select>
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
          <span>学生列表</span>
          <el-button type="primary" @click="handleAdd">新增学生</el-button>
        </div>
      </template>

      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="name" label="姓名" width="100" />
        <el-table-column prop="no" label="学号" width="120" />
        <el-table-column prop="gender" label="性别" width="70">
          <template #default="{ row }">{{ row.gender === 1 ? '男' : '女' }}</template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="idCard" label="身份证号" width="180" />
        <el-table-column prop="degree" label="学历" width="80">
          <template #default="{ row }">{{ degreeMap[row.degree] }}</template>
        </el-table-column>
        <el-table-column prop="clazzName" label="班级" width="120" />
        <el-table-column prop="graduationDate" label="毕业时间" width="120" />
        <el-table-column prop="violationCount" label="违纪次数" width="90" />
        <el-table-column prop="violationScore" label="违纪扣分" width="90" />
        <el-table-column label="操作" width="150" fixed="right">
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

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px" :close-on-click-modal="false">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学号" prop="no">
              <el-input v-model="form.no" placeholder="请输入学号" />
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
            <el-form-item label="身份证号" prop="idCard">
              <el-input v-model="form.idCard" placeholder="请输入身份证号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学历" prop="degree">
              <el-select v-model="form.degree" placeholder="请选择学历" style="width:100%">
                <el-option v-for="(v, k) in degreeMap" :key="k" :label="v" :value="Number(k)" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="班级" prop="clazzId">
              <el-select v-model="form.clazzId" placeholder="请选择班级" style="width:100%">
                <el-option v-for="c in clazzList" :key="c.id" :label="c.name" :value="c.id" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="毕业时间" prop="graduationDate">
              <el-date-picker v-model="form.graduationDate" type="date" placeholder="选择日期" style="width:100%" value-format="YYYY-MM-DD" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="地址" prop="address">
              <el-input v-model="form.address" placeholder="请输入地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="违纪次数">
              <el-input-number v-model="form.violationCount" :min="0" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="违纪扣分">
              <el-input-number v-model="form.violationScore" :min="0" style="width:100%" />
            </el-form-item>
          </el-col>
        </el-row>
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
import { getStudentPage, addStudent, updateStudent, deleteStudent, getStudentById } from '../../api/student'
import { getAllClazz } from '../../api/clazz'

const degreeMap = { 1: '初中', 2: '高中', 3: '大专', 4: '本科', 5: '硕士', 6: '博士' }

const searchForm = reactive({ name: '', degree: null, clazzId: null })
const tableData = ref([])
const loading = ref(false)
const page = ref(1)
const pageSize = ref(10)
const total = ref(0)
const clazzList = ref([])

const dialogVisible = ref(false)
const dialogTitle = ref('新增学生')
const isEdit = ref(false)
const submitLoading = ref(false)
const formRef = ref(null)

const form = reactive({
  id: null, name: '', no: '', gender: 1, phone: '', idCard: '',
  degree: 1, clazzId: null, graduationDate: '', address: '',
  isCollege: 0, violationCount: 0, violationScore: 0
})

const rules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  no: [{ required: true, message: '请输入学号', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
  idCard: [{ required: true, message: '请输入身份证号', trigger: 'blur' }],
  degree: [{ required: true, message: '请选择学历', trigger: 'change' }],
  clazzId: [{ required: true, message: '请选择班级', trigger: 'change' }]
}

const fetchClazzList = async () => {
  const res = await getAllClazz()
  clazzList.value = res.data || []
}

const fetchData = async () => {
  loading.value = true
  try {
    const params = { page: page.value, pageSize: pageSize.value }
    if (searchForm.name) params.name = searchForm.name
    if (searchForm.degree) params.degree = searchForm.degree
    if (searchForm.clazzId) params.clazzId = searchForm.clazzId
    const res = await getStudentPage(params)
    tableData.value = res.data.rows
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const resetSearch = () => {
  searchForm.name = ''
  searchForm.degree = null
  searchForm.clazzId = null
  fetchData()
}

const handleAdd = () => {
  isEdit.value = false
  dialogTitle.value = '新增学生'
  Object.assign(form, { id: null, name: '', no: '', gender: 1, phone: '', idCard: '', degree: 1, clazzId: null, graduationDate: '', address: '', isCollege: 0, violationCount: 0, violationScore: 0 })
  dialogVisible.value = true
}

const handleEdit = async (row) => {
  isEdit.value = true
  dialogTitle.value = '编辑学生'
  const res = await getStudentById(row.id)
  Object.assign(form, res.data)
  dialogVisible.value = true
}

const handleSubmit = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  submitLoading.value = true
  try {
    if (isEdit.value) {
      await updateStudent({ ...form })
      ElMessage.success('修改成功')
    } else {
      await addStudent({ ...form })
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    fetchData()
  } finally {
    submitLoading.value = false
  }
}

const handleDelete = async (id) => {
  await ElMessageBox.confirm('确定要删除该学生吗？', '提示', { type: 'warning' })
  try {
    await deleteStudent(id)
    ElMessage.success('删除成功')
    fetchData()
  } catch { /* 取消 */ }
}

onMounted(() => {
  fetchClazzList()
  fetchData()
})
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
.search-card :deep(.el-card__body) { padding-bottom: 0; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.pagination { margin-top: 16px; display: flex; justify-content: flex-end; }
</style>