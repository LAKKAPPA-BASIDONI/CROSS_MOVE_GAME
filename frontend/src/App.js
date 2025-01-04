import React, { useState, useEffect } from 'react';
import './components/GameBoard.css';
import { fetchAiMove, fetchAiToMove } from './components/api'; 

function App() {
  const [board, setBoard] = useState(Array(3).fill(null).map(() => Array(3).fill(null)));
  const [playerMoves, setPlayerMoves] = useState(0);
  const [aiMoves, setAiMoves] = useState(0);
  const [phase, setPhase] = useState('place');
  const [selectedPiece, setSelectedPiece] = useState(null);
  const [status, setStatus] = useState('Your turn to place your coins!');

  const PLAYER = 'X';
  const AI = 'O';
  

  useEffect(() => {
    if (playerMoves === 3 && aiMoves === 3) {
      setPhase('move');
      setStatus('Select a coin to move!');
    }
  }, [playerMoves, aiMoves]);

  const initBoard = () => {
    return board.map((row, rowIndex) => row.map((cell, colIndex) => ({
      row: rowIndex,
      col: colIndex,
      value: cell,
    })));
  };

  const handlePlayerClick = (row, col) => {
    if (phase === 'place') {
      if (playerMoves < 3 && !board[row][col]) {
        const newBoard = [...board];
        newBoard[row][col] = PLAYER;
        setBoard(newBoard);
        setPlayerMoves(playerMoves + 1);
        checkGameStatus(newBoard);
        aiMove(newBoard);
      }
    } else if (phase === 'move') {
      if (selectedPiece) {
        if (selectedPiece.row === row && selectedPiece.col === col) {
          setSelectedPiece(null);
          setStatus('Select a coin to move!');
        } else if (isValidMove(selectedPiece.row, selectedPiece.col, row, col)) {
          const newBoard = [...board];
          newBoard[selectedPiece.row][selectedPiece.col] = null;
          newBoard[row][col] = PLAYER;
          setBoard(newBoard);
          setSelectedPiece(null);
          checkGameStatus(newBoard);
          aiMove(newBoard);
        } else {
          setStatus('Invalid move! Select again.');
        }
      } else if (board[row][col] === PLAYER) {
        setSelectedPiece({ row, col });
        setStatus('Select a destination.');
      }
    }
  };

  const aiMove = (currentBoard) => {
    if (phase === 'place' && aiMoves < 3) {
      fetchAiMove(currentBoard).then(move => {
        const newBoard = [...currentBoard];
        newBoard[move?.frow][move?.fcol] = AI;
        setBoard(newBoard);
        setAiMoves(aiMoves + 1);
        checkGameStatus(newBoard);
      }).catch(error => {
          console.error("Error fetching AI move:", error);
      });
      
    } else if (phase === 'move') {
      fetchAiToMove(currentBoard).then(move => {
        const newBoard = [...currentBoard];
        newBoard[move?.trow][move?.tcol] = AI;
        newBoard[move?.frow][move?.fcol] = null;
        setBoard(newBoard);
        checkGameStatus(newBoard);

        }).catch(error => {
          console.error("Error fetching AI move:", error);
      });
    }
  };
  
  const isValidMove = (fromRow, fromCol, toRow, toCol) => {
    const adjacent = getAdjacentCells(fromRow, fromCol);
    return adjacent.some(([r, c]) => r === toRow && c === toCol && !board[r][c]);
  };

  const getAdjacentCells = (row, col) => {
    let directions = [
      [-1, 0], [1, 0], [0, -1], [0, 1],
    ];
    
    directions = directions.map(([dr, dc]) => [row + dr, col + dc]);
    if (row === 0 && col === 0 || row === 0 && col === 2 || row === 2 && col === 0 || row === 2 && col === 2) {
      directions = directions.concat([[1, 1]]);
    }

    if (row === 1 && col === 1) {
      directions = directions.concat([[0, 0], [0, 2], [2, 0], [2, 2]]);
    }

    return directions.filter(([r, c]) => r >= 0 && r < 3 && c >= 0 && c < 3);
  };

  const checkGameStatus = (currentBoard) => {
    const winner = checkWinner(currentBoard);
    if (winner) {
      setStatus(`${winner} wins!`);
      document.querySelectorAll('.cell').forEach(cell => cell.classList.add('taken'));
      setPhase('end'); 
    }
  };

  const checkWinner = (state) => {
    const lines = [
      [[0, 0], [0, 1], [0, 2]],
      [[1, 0], [1, 1], [1, 2]],
      [[2, 0], [2, 1], [2, 2]],
      [[0, 0], [1, 0], [2, 0]],
      [[0, 1], [1, 1], [2, 1]],
      [[0, 2], [1, 2], [2, 2]],
      [[0, 0], [1, 1], [2, 2]],
      [[0, 2], [1, 1], [2, 0]]
    ];

    for (const line of lines) {
      const [a, b, c] = line;
      if (state[a[0]][a[1]] && state[a[0]][a[1]] === state[b[0]][b[1]] && state[a[0]][a[1]] === state[c[0]][c[1]]) {
        return state[a[0]][a[1]];
      }
    }
    return null;
  };

  const renderBoard = () => {
    return board.map((row, rowIndex) => row.map((cell, colIndex) => (
      <div
        key={`${rowIndex}-${colIndex}`}
        className={`cell ${cell ? 'taken' : ''} ${selectedPiece && selectedPiece.row === rowIndex && selectedPiece.col === colIndex ? 'selected' : ''}`}
        onClick={() => handlePlayerClick(rowIndex, colIndex)}
      >
        {cell || ''}
      </div>
    )));
  };

  return (
    <div>
      <h1>CROSS MOVE GAME</h1>
      <div id="status">{status}</div>
      <div className="board">
        {renderBoard()}
      </div>
      <button onClick={() => window.location.reload()}>New Game</button>
    </div>
  );
}

export default App;
