import React from "react";

const IceCreamTableRow = ({
  id,
  name,
  description,
  price,
  image,
  shopName,
  onDelete,
  onUpdate,
}) => {
  return (
    <tr>
      <td>{id}</td>
      <td>{name}</td>
      <td>{description}</td>
      <td>{price}</td>
      <td>
        {image ? (
          <img
            src={`data:image/jpeg;base64,${image}`}
            alt="Ice cream"
            style={{
              width: 70,
              height: 70,
              objectFit: "cover",
              borderRadius: 10
            }}
          />
        ) : (
          "-"
        )}
      </td>
      <td>{shopName}</td>
      <td>
        <button className="btn" onClick={onUpdate}>
          Update
        </button>
        <button
          className="btn danger"
          style={{ marginLeft: 5 }}
          onClick={() => onDelete(id)}
        >
          Delete
        </button>
      </td>
    </tr>
  );
};

export default IceCreamTableRow;