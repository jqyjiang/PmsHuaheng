import request from '@/utils/request'

// 查询签署执行状态表列表
export function listStatus(query) {
  return request({
    url: '/procure/status/list',
    method: 'get',
    params: query
  })
}

// 查询签署执行状态表详细
export function getStatus(executionId) {
  return request({
    url: '/procure/status/' + executionId,
    method: 'get'
  })
}

// 新增签署执行状态表
export function addStatus(data) {
  return request({
    url: '/procure/status',
    method: 'post',
    data: data
  })
}

// 修改签署执行状态表
export function updateStatus(data) {
  return request({
    url: '/procure/status',
    method: 'put',
    data: data
  })
}

// 删除签署执行状态表
export function delStatus(executionId) {
  return request({
    url: '/procure/status/' + executionId,
    method: 'delete'
  })
}


