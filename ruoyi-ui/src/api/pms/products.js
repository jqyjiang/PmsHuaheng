import request from '@/utils/request'

// 查询 产品信息列表
export function listProducts(query) {
  return request({
    url: '/pms/products/list',
    method: 'get',
    params: query
  })
}

// 查询 产品信息详细
export function getProducts(productId) {
  return request({
    url: '/pms/products/' + productId,
    method: 'get'
  })
}

// 新增 产品信息
export function addProducts(data) {
  return request({
    url: '/pms/products',
    method: 'post',
    data: data
  })
}

// 修改 产品信息
export function updateProducts(data) {
  return request({
    url: '/pms/products',
    method: 'put',
    data: data
  })
}

// 删除 产品信息
export function delProducts(productId) {
  return request({
    url: '/pms/products/' + productId,
    method: 'delete'
  })
}
