import request from '@/utils/request'

// 查询送样列表
export function listSending(query) {
  return request({
    url: '/supplierpms/sending/list',
    method: 'get',
    params: query
  })
}

// 查询送样详细
export function getSending(sampleId) {
  return request({
    url: '/supplierpms/sending/' + sampleId,
    method: 'get'
  })
}

// 新增送样
export function addSending(data) {
  return request({
    url: '/supplierpms/sending',
    method: 'post',
    data: data
  })
}

// 修改送样
export function updateSending(data) {
  return request({
    url: '/supplierpms/sending',
    method: 'put',
    data: data
  })
}
// 修改送样状态
export function updataState(data) {
  return request({
    url: '/supplierpms/sending/updataState',
    method: 'put',
    data: data
  })
}
// 删除送样
export function delSending(sampleId) {
  return request({
    url: '/supplierpms/sending/' + sampleId,
    method: 'delete'
  })
}
