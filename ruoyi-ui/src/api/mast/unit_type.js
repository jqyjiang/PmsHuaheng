import request from '@/utils/request'

// 查询计量单位类型定义列表
export function listUnit_type(query) {
  return request({
    url: '/mast/unit_type/list',
    method: 'get',
    params: query
  })
}

// 查询计量单位类型定义详细
export function getUnit_type(unitTypeId) {
  return request({
    url: '/mast/unit_type/' + unitTypeId,
    method: 'get'
  })
}

// 新增计量单位类型定义
export function addUnit_type(data) {
  return request({
    url: '/mast/unit_type',
    method: 'post',
    data: data
  })
}

// 修改计量单位类型定义
export function updateUnit_type(data) {
  return request({
    url: '/mast/unit_type',
    method: 'put',
    data: data
  })
}

// 删除计量单位类型定义
export function delUnit_type(unitTypeId) {
  return request({
    url: '/mast/unit_type/' + unitTypeId,
    method: 'delete'
  })
}
