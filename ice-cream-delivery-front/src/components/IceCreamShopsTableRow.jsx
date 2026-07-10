import React from 'react'

const IceCreamShopsTableRow = ({ id, name, address, onDelete, onUpdate, image }) => {


  return (
    <tr key={id}>
      <td>{id}</td>
      <td>{name}</td>
      <td>{address}</td>
      <td>
        <img
          src={
            image
              ? `data:image/jpeg;base64,${image}`
              : "https://via.placeholder.com/80x60"
          }
          className="table-thumb"
          alt=""
        />
      </td>
      <td>
        <button className='btn delete' onClick={() => onDelete(id)}>Delete</button>
        <button className="btn update" onClick={() => onUpdate()}>Update</button>
      </td>
    </tr>
  )
}

export default IceCreamShopsTableRow
