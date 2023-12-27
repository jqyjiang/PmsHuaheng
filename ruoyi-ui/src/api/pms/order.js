import request from '@/utils/request'

// 查询质检单列表
export function listOrder(query) {
  return request({
    url: '/pms/order/list',
    method: 'get',
    params: query
  })
}

// 查询质检单详细
export function getOrder(qualityId) {
  return request({
    url: '/pms/order/' + qualityId,
    method: 'get'
  })
}

// 新增质检单
export function addOrder(data) {
  return request({
    url: '/pms/order',
    method: 'post',
    data: data
  })
}

// 修改质检单
export function updateOrder(data) {
  return request({
    url: '/pms/order',
    method: 'put',
    data: data
  })
}

// 删除质检单
export function delOrder(qualityId) {
  return request({
    url: '/pms/order/' + qualityId,
    method: 'delete'
  })
}
