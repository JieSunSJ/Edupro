<template>
  <div class="page-container">
    <el-row :gutter="16">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-item">
            <div class="stat-icon" style="background:#409EFF">
              <el-icon :size="28"><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ studentTotal }}</div>
              <div class="stat-label">学生总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-item">
            <div class="stat-icon" style="background:#67C23A">
              <el-icon :size="28"><OfficeBuilding /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ clazzTotal }}</div>
              <div class="stat-label">班级总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-item">
            <div class="stat-icon" style="background:#E6A23C">
              <el-icon :size="28"><School /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ activeClazzCount }}</div>
              <div class="stat-label">已开课班级</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-item">
            <div class="stat-icon" style="background:#9B59B6">
              <el-icon :size="28"><Avatar /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ teacherTotal }}</div>
              <div class="stat-label">教师总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16" style="margin-top:16px">
      <el-col :span="12">
        <el-card>
          <template #header><span>班级人数分布</span></template>
          <div ref="clazzChartRef" style="height:320px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header><span>性别分布</span></template>
          <div ref="genderChartRef" style="height:320px"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16" style="margin-top:16px">
      <el-col :span="12">
        <el-card>
          <template #header><span>班级状态分布</span></template>
          <div ref="statusChartRef" style="height:320px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header><span>学科分布</span></template>
          <div ref="subjectChartRef" style="height:320px"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import { User, OfficeBuilding, School, Avatar } from '@element-plus/icons-vue'
import * as echarts from 'echarts'
import { getStudentPage } from '../../api/student'
import { getAllClazz } from '../../api/clazz'
import { getTeachers } from '../../api/user'

const studentTotal = ref(0)
const clazzTotal = ref(0)
const activeClazzCount = ref(0)
const teacherTotal = ref(0)

const clazzChartRef = ref(null)
const genderChartRef = ref(null)
const statusChartRef = ref(null)
const subjectChartRef = ref(null)

const subjectMap = { 1: 'Java', 2: '前端', 3: 'Python', 4: 'UI', 5: '大数据', 6: '其他' }

const renderBarChart = (dom, title, data, colors) => {
  const chart = echarts.init(dom)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: {
      type: 'category',
      data: data.map(d => d.name),
      axisLabel: { fontSize: 12 }
    },
    yAxis: { type: 'value', minInterval: 1 },
    series: [{
      type: 'bar',
      data: data.map((d, i) => ({
        value: d.value,
        itemStyle: { color: colors ? colors[i % colors.length] : '#409EFF' }
      })),
      barWidth: '40%',
      label: { show: true, position: 'top', fontSize: 12 }
    }]
  })
  return chart
}

const renderPieChart = (dom, data, colors) => {
  const chart = echarts.init(dom)
  chart.setOption({
    tooltip: { trigger: 'item', formatter: '{b}: {c} ({d}%)' },
    legend: { bottom: 0, textStyle: { fontSize: 12 } },
    series: [{
      type: 'pie',
      radius: ['40%', '65%'],
      center: ['50%', '45%'],
      avoidLabelOverlap: false,
      label: { show: true, formatter: '{b}\n{d}%', fontSize: 12 },
      data: data.map((d, i) => ({
        name: d.name,
        value: d.value,
        itemStyle: { color: colors ? colors[i % colors.length] : undefined }
      }))
    }]
  })
  return chart
}

const charts = []

onMounted(async () => {
  try {
    const [stuRes, clazzRes, teacherRes] = await Promise.all([
      getStudentPage({ page: 1, pageSize: 1000 }),
      getAllClazz(),
      getTeachers()
    ])

    const students = stuRes.data.rows || []
    studentTotal.value = stuRes.data.total || 0

    const clazzes = clazzRes.data || []
    clazzTotal.value = clazzes.length
    activeClazzCount.value = clazzes.filter(c => c.status === 1).length

    teacherTotal.value = (teacherRes.data || []).length

    await nextTick()

    // 班级人数分布 - 柱状图
    const clazzStudentCount = {}
    students.forEach(s => {
      const cid = s.clazzId || s.clazz_id || 0
      clazzStudentCount[cid] = (clazzStudentCount[cid] || 0) + 1
    })
    const clazzBarData = Object.entries(clazzStudentCount).map(([k, v]) => {
      const clazz = clazzes.find(c => c.id === Number(k))
      return { name: clazz ? clazz.name : '未分班', value: v }
    }).sort((a, b) => b.value - a.value)
    const barColors = ['#409EFF', '#67C23A', '#E6A23C', '#F56C6C', '#9B59B6', '#909399']
    charts.push(renderBarChart(clazzChartRef.value, '班级人数', clazzBarData, barColors))

    // 性别分布 - 饼图
    const genderCount = { '男': 0, '女': 0 }
    students.forEach(s => {
      if (s.gender === 1) genderCount['男']++
      else genderCount['女']++
    })
    const genderData = Object.entries(genderCount)
      .filter(([, v]) => v > 0)
      .map(([k, v]) => ({ name: k, value: v }))
    charts.push(renderPieChart(genderChartRef.value, genderData, ['#409EFF', '#F56C6C']))

    // 班级状态分布 - 饼图 (不展示已完结)
    const statusMap = { 0: '未开课', 1: '已开课' }
    const statusColors = ['#909399', '#67C23A']
    const statusCount = {}
    clazzes.forEach(c => {
      if (c.status === 2) return
      const key = c.status || 0
      statusCount[key] = (statusCount[key] || 0) + 1
    })
    const statusData = Object.entries(statusCount).map(([k, v]) => ({
      name: statusMap[k] || '未知', value: v
    }))
    charts.push(renderPieChart(statusChartRef.value, statusData, statusColors))

    // 学科分布 - 饼图
    const subjectCount = {}
    clazzes.forEach(c => {
      const s = c.subject || 0
      subjectCount[s] = (subjectCount[s] || 0) + 1
    })
    const subjectData = Object.entries(subjectCount)
      .filter(([k]) => k !== '0')
      .map(([k, v]) => ({ name: subjectMap[k] || '其他', value: v }))
    const subjectColors = ['#409EFF', '#67C23A', '#E6A23C', '#F56C6C', '#9B59B6', '#909399']
    charts.push(renderPieChart(subjectChartRef.value, subjectData, subjectColors))

    window.addEventListener('resize', () => charts.forEach(c => c.resize()))
  } catch (e) {
    console.error('Failed to load report data', e)
  }
})
</script>

<style scoped>
.page-container { display: flex; flex-direction: column; gap: 16px; }
.stat-card { cursor: default; }
.stat-item { display: flex; align-items: center; gap: 16px; }
.stat-icon { width: 56px; height: 56px; border-radius: 12px; display: flex; align-items: center; justify-content: center; color: #fff; }
.stat-info { flex: 1; }
.stat-value { font-size: 28px; font-weight: bold; color: #303133; }
.stat-label { font-size: 14px; color: #909399; margin-top: 4px; }
</style>
