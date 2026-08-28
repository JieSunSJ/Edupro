<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header"><span>考试信息</span></div>
      </template>
      <el-table :data="examList" v-loading="loading" stripe>
        <el-table-column prop="examName" label="考试名称" min-width="180" />
        <el-table-column prop="courseName" label="课程" width="150" />
        <el-table-column prop="examDate" label="考试日期" width="120" />
        <el-table-column label="时间" width="160">
          <template #default="{ row }">{{ row.startTime }} - {{ row.endTime }}</template>
        </el-table-column>
        <el-table-column prop="room" label="教室" width="120" />
        <el-table-column label="类型" width="80">
          <template #default="{ row }">
            <el-tag :type="row.examType === 1 ? 'danger' : 'warning'" size="small">{{ row.examType === 1 ? '考试' : '考查' }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
      <el-empty v-if="!loading && examList.length === 0" description="暂无考试信息" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import request from '../../api/request'

const loading = ref(false)
const examList = ref([])

const loadExams = async () => {
  loading.value = true
  try {
    const res = await request.get('/student/exam')
    examList.value = res.data
  } finally {
    loading.value = false
  }
}

onMounted(loadExams)
</script>

<style scoped>
.page-container { max-width: 1200px; }
</style>
