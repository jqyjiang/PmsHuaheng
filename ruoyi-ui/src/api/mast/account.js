import request from '@/utils/request'

// 查询计算单位定义列表
export function listAccount(query) {
  return request({
    url: '/mast/account/list',
    method: 'get',
    params: query
  })
}

// 查询计量单位类型定义列表
export function listUnit_type(query) {
  return request({
    url: '/mast/unit_type/list',
    method: 'get',
    params: query
  })
}

// 查询计算单位定义详细
export function getAccount(unitId) {
  return request({
    url: '/mast/account/' + unitId,
    method: 'get'
  })
}

// 新增计算单位定义
export function addAccount(data) {
  return request({
    url: '/mast/account',
    method: 'post',
    data: data
  })
}

// 修改计算单位定义
export function updateAccount(data) {
  return request({
    url: '/mast/account',
    method: 'put',
    data: data
  })
}

// 删除计算单位定义
export function delAccount(unitId) {
  return request({
    url: '/mast/account/' + unitId,
    method: 'delete'
  })
}
