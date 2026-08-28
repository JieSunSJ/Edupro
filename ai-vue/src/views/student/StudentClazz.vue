<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header"><span>我的班级</span></div>
      </template>
      <div v-if="hasClazz" class="clazz-info">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="班级名称">{{ clazzName }}</el-descriptions-item>
          <el-descriptions-item label="教室">{{ clazzRoom }}</el-descriptions-item>
          <el-descriptions-item label="班主任">{{ clazzMaster }}</el-descriptions-item>
          <el-descriptions-item label="学科">{{ clazzSubject }}</el-descriptions-item>
          <el-descriptions-item label="开课日期">{{ clazzBegin }}</el-descriptions-item>
          <el-descriptions-item label="结课日期">{{ clazzEnd }}</el-descriptions-item>
        </el-descriptions>
      </div>
      <el-empty v-else description="你还没有分配班级" />
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const userInfo = JSON.parse(localStorage.getItem('studentInfo') || '{}')
const hasClazz = ref(!!userInfo.clazzId)
const clazzName = ref(userInfo.clazzName || '')
const clazzRoom = ref(userInfo.clazzRoom || '')
const clazzMaster = ref(userInfo.clazzMaster || '')
const clazzSubject = ref(userInfo.clazzSubject || '')
const clazzBegin = ref(userInfo.clazzBegin || '')
const clazzEnd = ref(userInfo.clazzEnd || '')
</script>

<style scoped>
.page-container { max-width: 1200px; }
.clazz-info { max-width: 700px; }
</style>
