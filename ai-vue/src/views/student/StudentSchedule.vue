<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header"><span>我的课表</span></div>
      </template>

      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="课程名称">
          <el-input v-model="searchForm.keyword" placeholder="输入课程名搜索" clearable @clear="searchForm.keyword='';loadSchedule()" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadSchedule">搜索</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="scheduleList" v-loading="loading" stripe>
        <el-table-column prop="courseName" label="课程名称" min-width="150" />
        <el-table-column prop="teacherName" label="授课教师" width="100" />
        <el-table-column label="星期" width="80">
          <template #default="{ row }">{{ weekDays[row.dayOfWeek] }}</template>
        </el-table-column>
        <el-table-column label="时间" width="160">
          <template #default="{ row }">{{ row.startTime }} - {{ row.endTime }}</template>
        </el-table-column>
        <el-table-column prop="room" label="教室" width="100" />
        <el-table-column label="周次" width="120">
          <template #default="{ row }">第{{ row.weekStart }}-{{ row.weekEnd }}周</template>
        </el-table-column>
      </el-table>
      <el-empty v-if="!loading && scheduleList.length === 0" description="暂无课表数据" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../../api/request'

const loading = ref(false)
const scheduleList = ref([])
const searchForm = ref({ keyword: '' })

const weekDays = { 1: '周一', 2: '周二', 3: '周三', 4: '周四', 5: '周五', 6: '周六', 7: '周日' }

const loadSchedule = async () => {
  loading.value = true
  try {
    const res = await request.get('/student/schedule', {
      params: { keyword: searchForm.value.keyword || undefined }
    })
    scheduleList.value = res.data
  } finally {
    loading.value = false
  }
}

onMounted(loadSchedule)
</script>

<style scoped>
.page-container { max-width: 1200px; }
.search-form { margin-bottom: 16px; }
</style>