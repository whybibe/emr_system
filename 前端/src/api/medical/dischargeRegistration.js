import request from '@/utils/request'

const api_name = '/admin/medical/discharge_registration'

export default {
  findList(param) {
    return request({
      url: `${api_name}/list`,
      method: 'GET',
      params: param
    })
  },
  // 保存电子病例
  saveDisch(data){
    return request({
      url: `${api_name}/save`,
      method: 'post',
      data: data
    })
  },
  // 一键结算
  settleAccounts(data) {
    return request({
      url: `${api_name}/settleAccounts`,
      method: 'GET',
      params: data
    })
  },
  deleteByIds(data) {
    return request({
      url: `${api_name}/delete`,
      method: 'delete',
      params: data
    })
  },
  upload(data){
    return request({
      url: `${api_name}/upload`,
      method: 'POST',
      data: data
    })
  }
}
