import request from '@/utils/request'

const api_name = '/admin/hosp/hospDepartment'

export default {

  findAllDep() {
    return request({
      url: `${api_name}/findAll/dep`,
      method: 'GET'
    })
  },
  findPatientNameByDepName(params){
    return request({
      url: `${api_name}/find/patientName`,
      method: 'GET',
      params : params
    })
  },
  depPatient(){
    return request({
      url: `${api_name}/tree/dep/patient`,
      method: 'GET'
    })
  },
  depList(data){
    return request({
      url: `${api_name}/list`,
      method: 'GET',
      params: data
    })
  },
  saveDep(data){
    return request({
      url: `${api_name}/save`,
      method: 'POST',
      data : data
    })
  },
  updateDep(data){
    return request({
      url: `${api_name}/update`,
      method: 'PUT',
      data : data
    })
  },
  removeById(data){
    return request({
      url: `${api_name}/delete`,
      method: 'DELETE',
      params: data
    })
  },
  removeByDepName(data){
    return request({
      url: `${api_name}/delete/dep`,
      method: 'DELETE',
      params: data
    })
  },
  updateStatus(data){
    return request({
      url: `${api_name}/update/status`,
      method: 'PUT',
      data: data
    })
  }
}
