import request from '@/utils/request'

// 查询统计分析供应商分类
export function listDetailsClass() {
    return request({
      url: '/supplierpms/details/listClass',
      method: 'get',
      
    })
  }
  // 查询统计分析供应商分类
export function listDetailsLife() {
    return request({
      url: '/supplierpms/details/listLife',
      method: 'get',
      
    })
  }