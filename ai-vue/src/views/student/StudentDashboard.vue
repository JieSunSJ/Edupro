<template>
  <div class="page-container">
    <el-card class="welcome-card">
      <div class="welcome-section">
        <h2>欢迎回来，{{ userName }}</h2>
        <p>当前班级：{{ clazzName }}</p>
      </div>
    </el-card>

    <el-row :gutter="16" class="stats-row">
      <el-col :span="8">
        <el-card>
          <el-statistic title="本学期课程" :value="courseCount" />
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <el-statistic title="今日课程" :value="todayCount" />
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <el-statistic title="违纪次数" :value="violationCount">
            <template #suffix>
              <span style="font-size:14px;color:#909399">次</span>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="today-card">
      <template #header>
        <div class="card-header"><span>今日课程</span></div>
      </template>
      <el-table :data="todayCourses" stripe v-loading="loading">
        <el-table-column prop="courseName" label="课程名称" min-width="150" />
        <el-table-column prop="teacherName" label="授课教师" width="100" />
        <el-table-column prop="room" label="教室" width="120" />
        <el-table-column label="时间" width="180">
          <template #default="{ row }">{{ row.startTime }} - {{ row.endTime }}</template>
        </el-table-column>
        <el-table-column label="节次" width="80">
          <template #default="{ row }">{{ getPeriodLabel(row) }}</template>
        </el-table-column>
      </el-table>
      <el-empty v-if="!loading && todayCourses.length === 0" description="今天没有课程" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../../api/request'

const userInfo = JSON.parse(localStorage.getItem('studentInfo') || '{}')
const userName = ref(userInfo.name || '同学')
const clazzName = ref(userInfo.clazzName || '未分配')

const courseCount = ref(0)
const todayCount = ref(0)
const violationCount = ref(0)
const todayCourses = ref([])
const loading = ref(false)

const timeSlots = ['08:30-10:00', '10:15-11:45', '14:00-15:30', '15:45-17:15', '19:00-20:30']

const getPeriodLabel = (row) => {
  const times = [
    { start: '08:30:00', end: '10:00:00' },
    { start: '10:15:00', end: '11:45:00' },
    { start: '14:00:00', end: '15:30:00' },
    { start: '15:45:00', end: '17:15:00' },
    { start: '19:00:00', end: '20:30:00' }
  ]
  const idx = times.findIndex(t => t.start === row.startTime && t.end === row.endTime)
  return idx >= 0 ? '第' + (idx + 1) + '节' : ''
}

const loadData = async () => {
  loading.value = true
  try {
    const [scheduleRes, courseRes] = await Promise.all([
      request.get('/student/schedule'),
      request.get('/student/course/status')
    ])
    const schedule = scheduleRes.data
    const status = courseRes.data
    courseCount.value = status.courseCount || 0
    violationCount.value = userInfo.violationCount || 0

    const today = new Date().getDay()
    const dayMap = { 0: 7, 1: 1, 2: 2, 3: 3, 4: 4, 5: 5, 6: 6 }
    const todayDay = dayMap[today]
    todayCourses.value = schedule.filter(s => s.dayOfWeek === todayDay)
    todayCount.value = todayCourses.value.length
  } finally {
    loading.value = false
  }
}

onMounted(loadData)
</script>

<style scoped>
.page-container { max-width: 1200px; }
.welcome-card { margin-bottom: 16px; }
.welcome-section h2 { margin-bottom: 8px; color: #303133; }
.welcome-section p { color: #606266; font-size: 15px; }
.stats-row { margin-bottom: 16px; }
.today-card { }
</style>
