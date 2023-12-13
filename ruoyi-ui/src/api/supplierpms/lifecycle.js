import request from '@/utils/request'

// 查询生命周期维护列表
export function listLifecycle(query) {
  return request({
    url: '/supplierpms/lifecycle/list',
    method: 'get',
    params: query
  })
}

// 查询生命周期维护详细
export function getLifecycle(lifecycleId) {
  return request({
    url: '/supplierpms/lifecycle/' + lifecycleId,
    method: 'get'
  })
}

// 新增生命周期维护
export function addLifecycle(data) {
  return request({
    url: '/supplierpms/lifecycle',
    method: 'post',
    data: data
  })
}

// 修改生命周期维护
export function updateLifecycle(data) {
  return request({
    url: '/supplierpms/lifecycle',
    method: 'put',
    data: data
  })
}

// 删除生命周期维护
export function delLifecycle(lifecycleId) {
  return request({
    url: '/supplierpms/lifecycle/' + lifecycleId,
    method: 'delete'
  })
}
