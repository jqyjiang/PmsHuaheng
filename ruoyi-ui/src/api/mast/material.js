import request from '@/utils/request'

// 查询物料维护列表
export function listMaterial(query) {
  return request({
    url: '/mast/material/list',
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

// 查询品类列表
export function listCategory(query) {
  return request({
    url: '/mast/category/listAll',
    method: 'get',
    params: query
  })
}

// 查询物料维护详细
export function getMaterial(materialId) {
  return request({
    url: '/mast/material/' + materialId,
    method: 'get'
  })
}

// 新增物料维护
export function addMaterial(data) {
  return request({
    url: '/mast/material',
    method: 'post',
    data: data
  })
}

// 修改物料维护
export function updateMaterial(data) {
  return request({
    url: '/mast/material',
    method: 'put',
    data: data
  })
}

// 删除物料维护
export function delMaterial(materialId) {
  return request({
    url: '/mast/material/' + materialId,
    method: 'delete'
  })
}
