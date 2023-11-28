import React, { useState, useEffect } from 'react';
import './NoticiaGeralData.css';
import { Link, useParams } from 'react-router-dom';

const NoticiaGeralData = ({ token}) => {
  const [noticias, setNoticias] = useState([]);
  const {selectedDate} = useParams();

  useEffect(() => {
    const fetchNoticias = async () => {
      try {
        const noticiasResponse = await fetch(`http://localhost:8080/v1/noticias/usuario/todas-as-noticias-do-usuario/com-data/?data=${selectedDate}`, {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });

        if (noticiasResponse.ok) {
          const noticiasData = await noticiasResponse.json();
          if (noticiasData) {
            setNoticias(noticiasData);
          } else {
            console.log('Dados da API estão incompletos:', noticiasData);
          }
        } else {
          console.error('Erro ao buscar notícias:', noticiasResponse.status);
        }
      } catch (error) {
        console.error('Erro durante a solicitação de notícias:', error);
      }
    };

    if (token && selectedDate) {
      fetchNoticias();
    }
  }, [token, selectedDate]);

  return (
    <div className="container mt-4">
      <div className="noticia-square">
        <div className="d-flex justify-content-between align-items-center">
          <h2>Notícias da Data Selecionada</h2>
          <Link to="/etiquetas" className="btn btn-success">
            Voltar
          </Link>
        </div>
        {noticias.length > 0 ? (
          noticias.map((noticia) => (
            noticia.items.map((item) => (
            <div key={item.titulo} className="card mb-3">
              <div className="card-body">
                <h2 className="card-title">{item.titulo}</h2>
                <p className="card-text">{item.introducao}</p>
                <p>Data de Publicação: {item.data_publicacao}</p>
                <p>Editorias: {item.editorias}</p>
              </div>
              <hr className="my-0" />
            </div>
            ))
          ))
        ) : (
          <p className="alert alert-info">Não há notícias na data selecionada!</p>
        )}
      </div>
    </div>
  );
}

export default NoticiaGeralData;