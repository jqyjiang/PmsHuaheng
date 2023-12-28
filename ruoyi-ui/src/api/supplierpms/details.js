import request from '@/utils/request'

// 查询供应商列表
export function listDetails(query) {
  return request({
    url: '/supplierpms/details/list',
    method: 'get',
    params: query
  })
}
//查询供应商列表已分类列表
export function listFilter(query) {
  return request({
    url: '/supplierpms/details/filter',
    method: 'get',
    params: query
  })
}
export function canSupplier(){
  return request({
    url: '/supplierpms/details/canSupplier',
    method: 'get'
  })
}
// 查询供应商列表详细
export function getDetails(sdId) {
  return request({
    url: '/supplierpms/details/' + sdId,
    method: 'get'
  })
}


// 新增供应商列表
export function addDetails(data) {
  return request({
    url: '/supplierpms/details',
    method: 'post',
    data: data
  })
}

//查询供应商审核过了的列表
export function lifeStage(query) {
  return request({
    url: '/supplierpms/details/lifeStage',
    method: 'get',
    params: query
  })
}
// 修改供应商列表
export function updateDetails(data) {
  return request({
    url: '/supplierpms/details',
    method: 'put',
    data: data
  })
}

// 修改供应商
export function Supplierlifecycle(data) {
  return request({
    url: '/supplierpms/details/updatelifecycle',
    method: 'put',
    data: data
  })
}

// 审核供应商周期
export function updateExamine(data){
  return request({
    url: '/supplierpms/details/updateExamine',
    method: 'put',
    data: data
  })
}
//批量删除供应商类别
export function delClassDetails(sdId) {
  return request({
    url: '/supplierpms/details/updateDetails/' + sdId,
    method: 'delete',

  })
}

//删除供应商列表
export function delDetails(sdId) {
  return request({
    url: '/supplierpms/details/' + sdId,
    method: 'delete'
  })
}
