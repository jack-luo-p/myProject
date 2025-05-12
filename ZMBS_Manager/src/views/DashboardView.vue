<template>
  <div class="dashboard">
    <h1>仪表盘</h1>
    
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover" class="data-card">
          <div class="data-card-content">
            <div class="data-number">{{ stats.userCount }}</div>
            <div class="data-title">用户总数</div>
          </div>
          <div class="data-icon">
            <el-icon><User /></el-icon>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover" class="data-card">
          <div class="data-card-content">
            <div class="data-number">{{ stats.orderCount }}</div>
            <div class="data-title">订单总数</div>
          </div>
          <div class="data-icon">
            <el-icon><ShoppingCart /></el-icon>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover" class="data-card">
          <div class="data-card-content">
            <div class="data-number">{{ stats.revenue }}</div>
            <div class="data-title">总收入</div>
          </div>
          <div class="data-icon">
            <el-icon><Money /></el-icon>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover" class="data-card">
          <div class="data-card-content">
            <div class="data-number">{{ stats.todayOrders }}</div>
            <div class="data-title">今日订单</div>
          </div>
          <div class="data-icon">
            <el-icon><Calendar /></el-icon>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="16">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>订单统计</span>
            </div>
          </template>
          <div style="height: 300px;">
            <!-- 这里可以放图表组件 -->
            <div class="chart-placeholder">订单数据图表区域</div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>系统公告</span>
            </div>
          </template>
          <div class="notification-list">
            <div v-for="(item, index) in notifications" :key="index" class="notification-item">
              <div class="notification-title">{{ item.title }}</div>
              <div class="notification-time">{{ item.time }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { User, ShoppingCart, Money, Calendar } from '@element-plus/icons-vue'

// 模拟数据
const stats = reactive({
  userCount: 1256,
  orderCount: 5893,
  revenue: '¥298,432',
  todayOrders: 128
})

const notifications = reactive([
  { title: '系统升级通知', time: '2023-10-05' },
  { title: '促销活动开始', time: '2023-10-03' },
  { title: '新功能上线公告', time: '2023-09-28' },
  { title: '系统维护通知', time: '2023-09-25' },
  { title: '节日优惠活动', time: '2023-09-20' }
])
</script>

<style scoped>
.dashboard {
  padding: 20px 0;
}

.data-card {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
}

.data-card-content {
  display: flex;
  flex-direction: column;
}

.data-number {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}

.data-title {
  margin-top: 8px;
  font-size: 14px;
  color: #909399;
}

.data-icon {
  font-size: 36px;
  color: #dcdfe6;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.chart-placeholder {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #909399;
  border: 1px dashed #dcdfe6;
}

.notification-list {
  max-height: 300px;
  overflow-y: auto;
}

.notification-item {
  padding: 10px 0;
  border-bottom: 1px solid #ebeef5;
}

.notification-item:last-child {
  border-bottom: none;
}

.notification-title {
  font-size: 14px;
  margin-bottom: 5px;
}

.notification-time {
  font-size: 12px;
  color: #909399;
}
</style> 