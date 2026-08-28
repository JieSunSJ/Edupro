<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div style="display:flex;justify-content:space-between;align-items:center">
          <span>操作日志</span>
          <el-button type="primary" :icon="Refresh" @click="fetchLogs">刷新</el-button>
        </div>
      </template>
      <el-table :data="logs" v-loading="loading" stripe border style="width:100%">
        <el-table-column prop="operateTime" label="操作时间" width="180">
          <template #default="{ row }">
            {{ formatTime(row.operateTime) }}
          </template>
        </el-table-column>
        <el-table-column prop="operateUser" label="操作人" width="120" />
        <el-table-column prop="action" label="操作类型" width="160">
          <template #default="{ row }">
            <el-tag :type="getTagType(row.action)">{{ row.action }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="detail" label="操作详情" min-width="200" show-overflow-tooltip />
        <el-table-column prop="costTime" label="耗时(ms)" width="100" align="center">
          <template #default="{ row }">
            <span :style="{ color: row.costTime > 500 ? '#F56C6C' : '#67C23A' }">{{ row.costTime }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="methodParams" label="参数" min-width="200" show-overflow-tooltip />
      </el-table>
      <div style="margin-top:16px;display:flex;justify-content:flex-end">
        <el-pagination
          v-model:current-page="page"
          v-model:page-size="pageSize"
          :total="total"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="fetchLogs"
          @current-change="fetchLogs"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Refresh } from '@element-plus/icons-vue'
import { getLogPage } from '../../api/log'

const logs = ref([])
const loading = ref(false)
const page = ref(1)
const pageSize = ref(10)
const total = ref(0)

const formatTime = (time) => {
  if (!time) return ''
  return time.replace('T', ' ').substring(0, 19)
}

const getTagType = (action) => {
  if (!action) return 'info'
  if (action.includes('新增')) return 'success'
  if (action.includes('修改')) return 'warning'
  if (action.includes('删除')) return 'danger'
  return 'info'
}

const fetchLogs = async () => {
  loading.value = true
  try {
    const res = await getLogPage({ page: page.value, pageSize: pageSize.value })
    logs.value = res.data.rows || []
    total.value = res.data.total || 0
  } catch (e) {
    console.error('获取日志失败', e)
  } finally {
    loading.value = false
  }
}

onMounted(() => fetchLogs())
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
</style>
