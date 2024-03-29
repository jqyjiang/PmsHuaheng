import request from '@/utils/request'

// 查询品类列表
export function listCategory(query) {
  return request({
    url: '/mast/category/list',
    method: 'get',
    params: query
  })
}

// 查询品类列表弹框
export function listCategory1(query) {
  return request({
    url: '/mast/category/list',
    method: 'get',
    params: query
  })
}

// 查询计算单位定义列表
export function listAccount(query) {
  return request({
    url: '/mast/account/listAll',
    method: 'get',
    params: query
  })
}

// 查询公司表列表
export function listCompanies(query) {
  return request({
    url: '/mast/company/list',
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

// 批量新增
export function batchCategory(data) {
  return request({
    url: '/mast/category/batchCategory',
    method: 'POST',
    headers: {
      'Content-Type': 'application/json' // 添加请求头部信息
    },
    data: data // 直接传递数组列表作为参数
  })
}


