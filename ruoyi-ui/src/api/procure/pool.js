import request from '@/utils/request'

// 查询采购需求池列表
export function listPool(query) {
  return request({
    url: '/procure/pool/list',
    method: 'get',
    params: query
  })
}

// 查询采购需求池详细
export function getPool(materialId) {
  return request({
    url: '/procure/pool/' + materialId,
    method: 'get'
  })
}

// 新增采购需求池
export function addPool(data) {
  return request({
    url: '/procure/pool',
    method: 'post',
    data: data
  })
}

// 修改采购需求池
export function updatePool(data) {
  return request({
    url: '/procure/pool',
    method: 'put',
    data: data
  })
}

// 删除采购需求池
export function delPool(materialId) {
  return request({
    url: '/procure/pool/' + materialId,
    method: 'delete'
  })
}

// 查询采购需求申请列表
export function listRequirement(query) {
  return request({
    url: '/procure/requirement/list',
    method: 'get',
    params: query
  })
}

// 查询采购需求池列表
export function listInformation(query) {
  return request({
    url: '/procure/information/list',
    method: 'get',
    params: query
  })
}

