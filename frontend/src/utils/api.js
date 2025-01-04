// Sending Move Request (Placing a Piece)
export const fetchAiMove = async (row, col, phase) => {
  const response = await fetch("http://localhost:8080/api/ai-move", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      phase: phase,
      row: row,
      col: col,
    }),
  });

  return await response.json();
  // Handle the response here (e.g., update UI with the new board state)
};

// Sending Move Request (Moving a Piece)
const sendMoveRequest = async (fromRow, fromCol, toRow, toCol, phase) => {
  const response = await fetch("http://localhost:8080/api/handle-move", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      phase: phase,
      fromRow: fromRow,
      fromCol: fromCol,
      toRow: toRow,
      toCol: toCol,
    }),
  });

  const data = await response.json();
  console.log(data);
  // Handle the response here (e.g., update UI with the new board state)
};


export const validateMove = async (from, to, board) => {
  try {
      const response = await fetch("http://localhost:8080/api/validate-move", {
          method: "POST",  // Ensure this is 'POST', not 'GET'
          headers: {
              "Content-Type": "application/json",
          },
          body: JSON.stringify({ from, to, board }),  // Ensure you're sending JSON data
      });
      return await response.json();
  } catch (error) {
      console.error("Error validating move:", error);
      return false;
  }
};
