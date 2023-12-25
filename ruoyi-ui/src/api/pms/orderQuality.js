import request from '@/utils/request'

// 查询到货质检列表
export function listOrderQuality(query) {
  return request({
    url: '/pms/orderQuality/list',
    method: 'get',
    params: query
  })
}

// 查询到货质检详细
export function getOrderQuality(qualityTaskListId) {
  return request({
    url: '/pms/orderQuality/' + qualityTaskListId,
    method: 'get'
  })
}

// 新增到货质检
export function addOrderQuality(data) {
  return request({
    url: '/pms/orderQuality',
    method: 'post',
    data: data
  })
}

// 修改到货质检
export function updateOrderQuality(data) {
  return request({
    url: '/pms/orderQuality',
    method: 'put',
    data: data
  })
}

// 删除到货质检
export function delOrderQuality(qualityTaskListId) {
  return request({
    url: '/pms/orderQuality/' + qualityTaskListId,
    method: 'delete'
  })
}
