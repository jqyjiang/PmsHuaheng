import request from '@/utils/request'

// 查询考评指标列表
export function listIndicators(query) {
  return request({
    url: '/supplierpms/indicators/list',
    method: 'get',
    params: query
  })
}

// 查询考评指标详细
export function getIndicators(indicatorId) {
  return request({
    url: '/supplierpms/indicators/' + indicatorId,
    method: 'get'
  })
}

// 新增考评指标
export function addIndicators(data) {
  return request({
    url: '/supplierpms/indicators',
    method: 'post',
    data: data
  })
}

// 修改考评指标
export function updateIndicators(data) {
  return request({
    url: '/supplierpms/indicators',
    method: 'put',
    data: data
  })
}

// 删除考评指标
export function delIndicators(indicatorId) {
  return request({
    url: '/supplierpms/indicators/' + indicatorId,
    method: 'delete'
  })
}
