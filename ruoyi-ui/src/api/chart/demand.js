import request from '@/utils/request'

// 查询统计分析月度需求数量
export function listMonthDemand() {
    return request({
      url: '/mast/material/listMonthDemand',
      method: 'get',
    })
  }

//查询统计分析需求类型数量
export function listDemandType() {
    return request({
      url: '/procure/requirement/listDemandType',
      method: 'get',
    })
  }
//查询统计分析需求状态数量
export function listDemandStatus() {
    return request({
      url: '/mast/material/listDemandStatus',
      method: 'get',
    })
  }