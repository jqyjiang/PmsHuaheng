import request from '@/utils/request'


// 查询采购需求池详细
export function getInformation(miId) {
  return request({
    url: '/procure/information/' + miId,
    method: 'get'
  })
}

// 新增采购需求池
export function addInformation(data) {
  return request({
    url: '/procure/information',
    method: 'post',
    data: data
  })
}

// 修改采购需求池
export function updateInformation(data) {
  return request({
    url: '/procure/information',
    method: 'put',
    data: data
  })
}

// 删除采购需求池
export function delInformation(miId) {
  return request({
    url: '/procure/information/' + miId,
    method: 'delete'
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

// 修改状态

//暂挂
export function editStatus(miId) {
  return request({
    url: '/procure/information/zg/'+miId,
    method: 'put'
  })
}

//作废
export function editStatusCancel(miId) {
  return request({
    url: '/procure/information/zf/'+miId,
    method: 'put'
  })
}
//分配
export function editStatusAllocation(purchaser,miId) {
  return request({
    url: '/procure/information/fp/'+purchaser+'/'+miId,
    method: 'put'
  })
}
export function editStatusProcurementTask(purchaser,requirementCode) {
  return request({
    url: '/procure/information/dfp/'+purchaser+'/'+requirementCode,
    method: 'put'
  })
}
// 查询采购员列表
export function listBuyer(query) {
  return request({
    url: '/procure/buyer/list',
    method: 'get',
    params: query
  })
}


