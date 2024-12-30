import request from '@/utils/request'

const api_name = '/admin/info/dict'

export default {

  findList() {
    return request({
      url: `${api_name}/list`,
      method: 'GET'
    })
  },
  deleteById(data) {
    return request({
      url: `${api_name}/delete`,
      method: 'delete',
      data: data
    })
  },
  saveDict(data) {
    return request({
      url: `${api_name}/save`,
      method: 'POST',
      data: data
    })
  },
  findById(id) {
    return request({
      url: `${api_name}/info/${id}`,
      method: 'GET'
    })
  },
  updateById(data) {
    return request({
      url: `${api_name}/update`,
      method: 'PUT',
      data: data
    })
  },
  certificatesType(){
    return request({
      url: `${api_name}/anno/certificates`,
      method: 'GET'
    })
  },
  getDictDwMap(){
    return request({
      url: `${api_name}/anno/dw/info`,
      method: 'GET'
    })
  }
}
