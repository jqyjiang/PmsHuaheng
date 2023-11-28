import request from '@/utils/request'

// 查询税率列表
export function listRate(query) {
  return request({
    url: '/mast/rate/list',
    method: 'get',
    params: query
  })
}

// 查询税率详细
export function getRate(taxTypeId) {
  return request({
    url: '/mast/rate/' + taxTypeId,
    method: 'get'
  })
}

// 新增税率
export function addRate(data) {
  return request({
    url: '/mast/rate',
    method: 'post',
    data: data
  })
}

// 修改税率
export function updateRate(data) {
  return request({
    url: '/mast/rate',
    method: 'put',
    data: data
  })
}

// 删除税率
export function delRate(taxTypeId) {
  return request({
    url: '/mast/rate/' + taxTypeId,
    method: 'delete'
  })
}
