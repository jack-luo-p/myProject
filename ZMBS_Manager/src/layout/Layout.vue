<template>
  <div class="app-layout">
    <side-menu ref="sideMenuRef" />
    
    <div class="main-container" :class="{ 'is-collapsed': isMenuCollapsed }">
      <header-nav />
      
      <div class="content">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import SideMenu from '@/components/SideMenu.vue'
import HeaderNav from '@/components/HeaderNav.vue'

const sideMenuRef = ref()
const isMenuCollapsed = computed(() => {
  return sideMenuRef.value?.isCollapsed || false
})
</script>

<style scoped>
.app-layout {
  height: 100vh;
  display: flex;
  overflow: hidden;
}

.main-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  transition: margin-left 0.3s;
  margin-left: 210px;
  overflow: auto;
}

.main-container.is-collapsed {
  margin-left: 64px;
}

.content {
  flex: 1;
  padding: 20px;
  background-color: #f0f2f5;
  overflow: auto;
}
</style> 