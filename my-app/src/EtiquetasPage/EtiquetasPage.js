import React, { useState, useEffect } from 'react';
import './EtiquetasPage.css';
import { Link } from 'react-router-dom';

const EtiquetasPage = ({ token }) => {
  const [etiquetas, setEtiquetas] = useState([]);
  const [selectedDate, setSelectedDate] = useState('');

  const handleDateChange = (event) => {
    setSelectedDate(event.target.value);
  };

  useEffect(() => {
    const fetchEtiquetas = async () => {
      try {
        const response = await fetch('http://localhost:8080/v1/etiquetas/usuario', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });

        if (response.ok) {
          const data = await response.json();
          setEtiquetas(data.content);
        } else {
          console.error('Erro ao buscar etiquetas:', response.status);
        }
      } catch (error) {
        console.error('Erro durante a chamada da API:', error);
      }
    };

    fetchEtiquetas();
  }, [token]);

  return (
    <div className="etiquetas-container">
      <div className="quadrado">
        <h2>Lista de Etiquetas</h2>
        <div className="etiquetas-header">
          <Link to="/etiquetas/create" className="btn btn-primary">Criar Nova Etiqueta</Link>
          <Link to="/noticias/dia" className="btn btn-success">Notícias do Dia</Link>
          <div className="etiquetas-date-search">
          <input
            type="date"
            value={selectedDate}
            onChange={handleDateChange}
            className="form-control"
          />
          <Link to={`/noticias/dia/${selectedDate}`} className="btn btn-info">Notícias da Data</Link>
        </div>
        </div>
        <table className="etiquetas-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nome da Etiqueta</th>
              <th>Notícias Do Dia</th>
            </tr>
          </thead>
          <tbody>
            {etiquetas.map((etiqueta) => (
              <tr key={etiqueta.id} className="etiqueta-item">
                <td>{etiqueta.id}</td>
                <td>{etiqueta.nomeEtiqueta}</td>
                <td>
                  <Link to={`/noticias/${etiqueta.id}`} className='btn btn-primary'>Ver Notícias</Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default EtiquetasPage;