<template>
  <div class="page-container">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card class="chart-card">
          <template #header><span>各职位员工人数</span></template>
          <div ref="jobChartRef" class="chart-box"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="chart-card">
          <template #header><span>员工性别统计</span></template>
          <div ref="genderChartRef" class="chart-box"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top:20px">
      <el-col :span="24">
        <el-card class="chart-card">
          <template #header><span>各班级学生人数</span></template>
          <div ref="clazzChartRef" class="chart-box-large"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import { getEmpJobData, getEmpGenderData, getStudentClazzData } from '../../api/report'

const jobChartRef = ref(null)
const genderChartRef = ref(null)
const clazzChartRef = ref(null)
let charts = []

const initJobChart = async () => {
  const res = await getEmpJobData()
  const { jobList, dataList } = res.data
  const chart = echarts.init(jobChartRef.value)
  chart.setOption({
    tooltip: { trigger: 'item' },
    legend: { bottom: 0 },
    series: [{
      name: '员工职位分布',
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      label: { show: true, position: 'outside' },
      data: jobList.map((name, i) => ({ name, value: dataList[i] }))
    }]
  })
  charts.push(chart)
}

const initGenderChart = async () => {
  const res = await getEmpGenderData()
  const data = res.data
  const chart = echarts.init(genderChartRef.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: data.map(d => d.name) },
    yAxis: { type: 'value' },
    series: [{
      name: '人数',
      type: 'bar',
      data: data.map(d => d.value),
      itemStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: '#409EFF' },
          { offset: 1, color: '#79bbff' }
        ])
      }
    }]
  })
  charts.push(chart)
}

const initClazzChart = async () => {
  const res = await getStudentClazzData()
  const { clazzList, countList } = res.data
  const chart = echarts.init(clazzChartRef.value)
  chart.setOption({
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: clazzList, axisLabel: { rotate: 30 } },
    yAxis: { type: 'value' },
    series: [{
      name: '学生人数',
      type: 'bar',
      data: countList,
      itemStyle: { color: '#67C23A' },
      label: { show: true, position: 'top' }
    }]
  })
  charts.push(chart)
}

onMounted(() => {
  initJobChart()
  initGenderChart()
  initClazzChart()
})

onUnmounted(() => {
  charts.forEach(c => c.dispose())
})
</script>

<style scoped>
.page-container { padding: 0; }
.chart-card { margin-bottom: 0; }
.chart-box { width: 100%; height: 350px; }
.chart-box-large { width: 100%; height: 400px; }
</style>