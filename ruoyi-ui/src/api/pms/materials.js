import request from '@/utils/request'

// 查询订单物料明细列表
export function listMaterials(query) {
  return request({
    url: '/pms/materials/list',
    method: 'get',
    params: query
  })
}

// 查询订单物料明细详细
export function getMaterial(orId) {
  return request({
    url: '/pms/materials/' + orId,
    method: 'get'
  })
}

// 根据任务单号查询订单物料明细详细
export function findTaskMaterial(taskCode) {
  return request({
    url: '/pms/materials/findTaskMaterial/' + taskCode,
    method: 'get'
  })
}
// 根据来源单号查询订单物料明细详细
export function findByOrderCodeMaterial(orderCode) {
  return request({
    url: '/pms/materials/findByOrderCodeMaterial/' + orderCode,
    method: 'get'
  })
}
// 新增订单物料明细
export function addMaterial(data) {
  return request({
    url: '/pms/materials',
    method: 'post',
    data: data
  })
}
// 新增订单物料明细
export function addMaterials(data) {
  return request({
    url: '/pms/materials/addMaterials',
    method: 'post',
    data: data
  })
}
// 修改订单物料明细
export function updateMaterial(data) {
  return request({
    url: '/pms/materials',
    method: 'put',
    data: data
  })
}

// 删除订单物料明细
export function delMaterial(orId) {
  return request({
    url: '/pms/materials/' + orId,
    method: 'delete'
  })
}

// 删除订单物料明细
export function deleteMaterial(orderCode) {
  return request({
    url: '/pms/materials/deleteMaterial/' + orderCode,
    method: 'delete'
  })
}