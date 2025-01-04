import React from "react";

const Cell = ({ row, col, value, isSelected, handleCellClick }) => {
  return (
    <div
      className={`cell ${value ? "taken" : ""} ${isSelected ? "selected" : ""}`}
      onClick={() => handleCellClick(row, col)}
    >
      {value}
    </div>
  );
};

export default Cell;
