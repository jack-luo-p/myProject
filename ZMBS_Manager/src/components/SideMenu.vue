<template>
  <div class="side-menu" :class="{ 'is-collapsed': isCollapsed }">
    <div class="logo-container">
      <img src="@/assets/logo.png" alt="ZMBS Logo" class="logo">
      <h2 class="title" v-show="!isCollapsed">ZMBS系统</h2>
    </div>
    
    <el-menu
      :default-active="activeIndex"
      class="menu"
      :collapse="isCollapsed"
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409EFF"
      :collapse-transition="false"
      :unique-opened="true"
      router
    >
      <el-menu-item index="/">
        <el-icon><HomeFilled /></el-icon>
        <template #title>首页</template>
      </el-menu-item>
      
      <el-sub-menu index="1">
        <template #title>
          <el-icon><User /></el-icon>
          <span>用户管理</span>
        </template>
        <el-menu-item index="/user/list">用户列表</el-menu-item>
        <el-menu-item index="/user/add">添加用户</el-menu-item>
      </el-sub-menu>
      
      <el-sub-menu index="2">
        <template #title>
          <el-icon><ShoppingCart /></el-icon>
          <span>订单管理</span>
        </template>
        <el-menu-item index="/order/list">订单列表</el-menu-item>
        <el-menu-item index="/order/statistics">订单统计</el-menu-item>
      </el-sub-menu>
      
      <el-sub-menu index="3">
        <template #title>
          <el-icon><Setting /></el-icon>
          <span>系统设置</span>
        </template>
        <el-menu-item index="/settings/profile">个人信息</el-menu-item>
        <el-menu-item index="/settings/security">安全设置</el-menu-item>
      </el-sub-menu>
    </el-menu>
    
    <div class="collapse-btn" @click="toggleCollapse">
      <el-icon v-if="isCollapsed"><ArrowRight /></el-icon>
      <el-icon v-else><ArrowLeft /></el-icon>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { 
  HomeFilled, 
  User, 
  ShoppingCart, 
  Setting, 
  ArrowRight, 
  ArrowLeft 
} from '@element-plus/icons-vue'

const route = useRoute()
const isCollapsed = ref(false)

const activeIndex = computed(() => {
  return route.path
})

const toggleCollapse = () => {
  isCollapsed.value = !isCollapsed.value
}

defineExpose({
  isCollapsed
})
</script>

<style scoped>
.side-menu {
  height: 100%;
  background-color: #304156;
  transition: width 0.3s;
  width: 210px;
  position: relative;
}

.side-menu.is-collapsed {
  width: 64px;
}

.logo-container {
  height: 60px;
  display: flex;
  align-items: center;
  padding: 0 16px;
  overflow: hidden;
}

.logo {
  width: 32px;
  height: 32px;
  margin-right: 10px;
}

.title {
  color: white;
  font-size: 18px;
  font-weight: 600;
  white-space: nowrap;
  margin: 0;
}

.menu {
  border-right: none;
  width: 100%;
}

.collapse-btn {
  position: absolute;
  bottom: 20px;
  left: 0;
  right: 0;
  text-align: center;
  color: #bfcbd9;
  cursor: pointer;
  padding: 8px 0;
}

.collapse-btn:hover {
  background-color: rgba(255, 255, 255, 0.1);
}
</style> 