<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <el-button text @click="$router.back()"><el-icon><ArrowLeft /></el-icon> 返回</el-button>
          <span>班级详情</span>
        </div>
      </template>
      <el-empty description="班级详情开发中..." />
    </el-card>
  </div>
</template>

<script setup>
import { ArrowLeft } from '@element-plus/icons-vue'
</script>

<style scoped>
.page-container { max-width: 1200px; }
</style>