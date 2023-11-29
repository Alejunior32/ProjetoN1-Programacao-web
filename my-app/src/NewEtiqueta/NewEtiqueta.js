import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './NewEtiqueta.css';

const NewEtiqueta = ({ token }) => {
  const [nomeEtiqueta, setNomeEtiqueta] = useState('');
  const [createdEtiqueta, setCreatedEtiqueta] = useState(null);

  const handleCreateEtiqueta = async () => {
    try {
      const response = await fetch('http://localhost:8080/v1/etiquetas/usuario', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          nomeEtiqueta,
        }),
      });

      if (response.ok) {
        const data = await response.json();
        setCreatedEtiqueta(data);
      } else {
        console.error('Erro ao criar etiqueta:', response.status);
      }
    } catch (error) {
      console.error('Erro durante a chamada da API:', error);
    }
  };

  return (
    <div className="newEtiqueta-container">
      <div className="newQuadrado">
        <h2>Crie uma nova Etiqueta</h2>
        <form onSubmit={(e) => {
          e.preventDefault();
          handleCreateEtiqueta();
        }}>
          <label htmlFor="nomeEtiqueta">Nome da Etiqueta:</label>
          <input
            type="text"
            id="nomeEtiqueta"
            value={nomeEtiqueta}
            onChange={(e) => setNomeEtiqueta(e.target.value)}
          />
          <button type="submit">Criar Etiqueta</button>
        </form>
        {createdEtiqueta && (
          <div>
            <h3>Etiqueta Criada com Sucesso!</h3>
            <p>Nome da Etiqueta: {createdEtiqueta.nomeEtiqueta}</p>
          </div>
        )}
        <Link to="/etiquetas">
            Voltar
          </Link>
      </div>
    </div>
  );
};

export default NewEtiqueta;