import request from '@/utils/request'

// 查询供货管理列表
export function listSupply(query) {
  return request({
    url: '/supplierpms/supply/list',
    method: 'get',
    params: query
  })
}

// 查询供货管理详细
export function getSupply(supplyId) {
  return request({
    url: '/supplierpms/supply/' + supplyId,
    method: 'get'
  })
}

// 新增供货管理
export function addSupply(data) {
  return request({
    url: '/supplierpms/supply',
    method: 'post',
    data: data
  })
}

// 修改供货管理
export function updateSupply(data) {
  return request({
    url: '/supplierpms/supply',
    method: 'put',
    data: data
  })
}

// 删除供货管理
export function delSupply(supplyId) {
  return request({
    url: '/supplierpms/supply/' + supplyId,
    method: 'delete'
  })
}

// 简单查询公司
export function companies() {
  return request({
    url: '/supplierpms/supply/query',
    method: 'get',
  })
}
