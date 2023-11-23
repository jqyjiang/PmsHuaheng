import request from '@/utils/request'

// 查询品类列表
export function listCategory(query) {
  return request({
    url: '/mast/category/list',
    method: 'get',
    params: query
  })
}

// 查询品类详细
export function getCategory(categoryid) {
  return request({
    url: '/mast/category/' + categoryid,
    method: 'get'
  })
}

// 新增品类
export function addCategory(data) {
  return request({
    url: '/mast/category',
    method: 'post',
    data: data
  })
}

// 修改品类
export function updateCategory(data) {
  return request({
    url: '/mast/category',
    method: 'put',
    data: data
  })
}

// 删除品类
export function delCategory(categoryid) {
  return request({
    url: '/mast/category/' + categoryid,
    method: 'delete'
  })
}
