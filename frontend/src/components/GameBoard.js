import React from "react";
import "./GameBoard.css";

const GameBoard = ({ board, selectedPiece, handleCellClick }) => {
  return (
    <div className="board">
      {board.map((row, i) =>
        row.map((cell, j) => (
          <div
            key={`${i}-${j}`}
            className={`cell ${cell ? "taken" : ""} ${
              selectedPiece && selectedPiece.row === i && selectedPiece.col === j ? "selected" : ""
            }`}
            onClick={() => handleCellClick(i, j)}
          >
            {cell}
          </div>
        ))
      )}
    </div>
  );
};

export default GameBoard;
