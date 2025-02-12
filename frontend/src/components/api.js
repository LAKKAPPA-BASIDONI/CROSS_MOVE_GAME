export const fetchAiMove = async (state) => {
  const response = await fetch("http://localhost:8080/api/getBestMove", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(state),
  });
  const data = await response.json();
  return data;
};
  

export  const fetchAiToMove = async (state) => {
  const response = await fetch("http://localhost:8080/api/getBestMoveToMove", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(state),
  });

  const data = await response.json();
  return data;
};
  