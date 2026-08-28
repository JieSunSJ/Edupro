<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>选课中心</span>
          <el-tag :type="enrollPeriod.isOpen ? 'success' : 'warning'" size="large">
            {{ enrollPeriod.statusText }}
          </el-tag>
        </div>
      </template>

      <div v-if="myCourse" class="my-course-card">
        <el-alert type="success" :closable="false" show-icon>
          <template #title>
            <span>已选课程：{{ myCourse.courseName }} | 授课教师：{{ myCourse.teacherName }} | 教室：{{ myCourse.room }}</span>
          </template>
          <template #default>
            <el-popconfirm v-if="enrollPeriod.isOpen" title="确定要退选该课程吗？" @confirm="handleUnenroll">
              <template #reference>
                <el-button type="danger" size="small">退课</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-alert>
      </div>

      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="课程名称">
          <el-input v-model="searchForm.name" placeholder="输入课程名搜索" clearable @clear="searchForm.name='';loadCourses()" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadCourses">搜索</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="courseList" v-loading="loading" stripe>
        <el-table-column prop="name" label="课程名称" min-width="150" />
        <el-table-column prop="teacherName" label="授课教师" width="100" />
        <el-table-column prop="room" label="教室" width="120" />
        <el-table-column label="选课人数" width="130">
          <template #default="{ row }">
            <span :style="{ color: row.enrolledCount >= row.capacity ? '#f56c6c' : '#67c23a', fontWeight: 'bold' }">
              {{ row.enrolledCount }}/{{ row.capacity }}
            </span>
            <el-progress :percentage="Math.round(row.enrolledCount / row.capacity * 100)" 
              :color="row.enrolledCount >= row.capacity ? '#f56c6c' : '#67c23a'"
              :stroke-width="6" :show-text="false" style="margin-top:3px" />
          </template>
        </el-table-column>
        <el-table-column prop="description" label="课程简介" min-width="200" show-overflow-tooltip />
        <el-table-column label="操作" width="100" fixed="right">
          <template #default="{ row }">
            <el-button v-if="enrollPeriod.isOpen && !hasCourse" type="primary" size="small" 
              :disabled="row.enrolledCount >= row.capacity" @click="handleEnroll(row)">
              {{ row.enrolledCount >= row.capacity ? '已满' : '选课' }}
            </el-button>
            <el-tag v-else-if="hasCourse && myCourse && myCourse.courseId === row.id" type="success">已选</el-tag>
            <el-tag v-else-if="!enrollPeriod.isOpen" type="info">未开放</el-tag>
            <el-tag v-else type="info">-</el-tag>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-wrap">
        <el-pagination
          v-model:current-page="page"
          :page-size="pageSize"
          :total="total"
          layout="total, prev, pager, next"
          @current-change="loadCourses"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import request from '../../api/request'

const searchForm = ref({ name: '' })
const courseList = ref([])
const myCourse = ref(null)
const hasCourse = ref(false)
const loading = ref(false)
const page = ref(1)
const pageSize = ref(10)
const total = ref(0)
const enrollPeriod = ref({ isOpen: false, statusText: '加载中...' })

const loadCourses = async () => {
  loading.value = true
  try {
    const res = await request.get('/student/course/available', {
      params: { 
        name: searchForm.value.name || undefined, 
        page: page.value, 
        pageSize: pageSize.value 
      }
    })
    courseList.value = res.data.rows
    total.value = res.data.total
  } finally {
    loading.value = false
  }
}

const loadMyCourse = async () => {
  try {
    const res = await request.get('/student/course/my')
    if (res.data && res.data.length > 0) {
      myCourse.value = res.data[0]
      hasCourse.value = true
    } else {
      myCourse.value = null
      hasCourse.value = false
    }
  } catch (e) { /* ignore */ }
}

const loadEnrollPeriod = async () => {
  try {
    const res = await request.get('/student/enroll-period')
    enrollPeriod.value = res.data
  } catch (e) { /* ignore */ }
}

const handleEnroll = async (row) => {
  try {
    await request.post(`/student/course/enroll/${row.id}`)
    ElMessage.success('选课成功')
    await loadMyCourse()
    await loadCourses()
  } catch (e) {
    ElMessage.error(e.response?.data?.msg || '选课失败')
  }
}

const handleUnenroll = async () => {
  try {
    await request.delete(`/student/course/unenroll/${myCourse.value.courseId}`)
    ElMessage.success('退课成功')
    myCourse.value = null
    hasCourse.value = false
    await loadCourses()
  } catch (e) {
    ElMessage.error(e.response?.data?.msg || '退课失败')
  }
}

onMounted(async () => {
  await Promise.all([loadEnrollPeriod(), loadMyCourse(), loadCourses()])
})
</script>

<style scoped>
.page-container { max-width: 1200px; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-top: 16px; margin-bottom: 16px; }
.pagination-wrap { margin-top: 16px; display: flex; justify-content: flex-end; }
.my-course-card { margin-bottom: 16px; }
</style>