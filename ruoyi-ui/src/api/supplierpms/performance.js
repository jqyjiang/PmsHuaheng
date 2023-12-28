import request from '@/utils/request'

// 查询绩效考评列表
export function listPerformance(query) {
  return request({
    url: '/supplierpms/performance/list',
    method: 'get',
    params: query
  })
}

// 查询绩效考评详细
export function getPerformance(evaluationId) {
  return request({
    url: '/supplierpms/performance/' + evaluationId,
    method: 'get'
  })
}

// 新增绩效考评
export function addPerformance(data) {
  return request({
    url: '/supplierpms/performance',
    method: 'post',
    data: data
  })
}

// 修改绩效考评
export function updatePerformance(data) {
  return request({
    url: '/supplierpms/performance',
    method: 'put',
    data: data
  })
}

// 删除绩效考评
export function delPerformance(evaluationId) {
  return request({
    url: '/supplierpms/performance/' + evaluationId,
    method: 'delete'
  })
}

// 查询品类列表
export function listCategory(query) {
  return request({
    url: '/mast/category/list',
    method: 'get',
    params: query
  })
}
