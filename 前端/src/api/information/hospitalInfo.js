import request from '@/utils/request'

const api_name = '/admin/info/hospital'

export default {

  findList() {
    return request({
      url: `${api_name}/info`,
      method: 'GET'
    })
  },
  saveHospital(data) {
    return request({
      url: `${api_name}/save`,
      method: 'post',
      data: data
    })
  },
  updateHospital(data) {
    return request({
      url: `${api_name}/update`,
      method: 'put',
      data: data
    })
  }
}
