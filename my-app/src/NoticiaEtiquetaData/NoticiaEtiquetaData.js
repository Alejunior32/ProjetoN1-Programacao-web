import React, { useState, useEffect } from 'react';
import './NoticiaEtiquetaData.css';
import { Link, useParams } from 'react-router-dom';

const NoticiaEtiquetaData = ({ token }) => {
  const [noticias, setNoticias] = useState([]);
  const {idEtiqueta, selectedDate} = useParams();

  useEffect(() => {
    const fetchNoticias = async () => {
      try {
        const noticiasResponse = await fetch(`http://localhost:8080/v1/noticias/usuario/com-data/?data=${selectedDate}&etiquetaId=${idEtiqueta}`, {
            method: 'GET',
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json',
            },
          });
  
          if (noticiasResponse.ok) {
            const noticiasData = await noticiasResponse.json();
            setNoticias(noticiasData);
             if (!noticiasData) {
              console.log('Dados da API estão incompletos:', noticiasData);
            }
          } else {
            console.error('Erro ao buscar notícias:', noticiasResponse.status);
          }
        } catch (error) {
          console.error('Erro durante a solicitação de notícias:', error);
        }
      };
  
      if (token && idEtiqueta && selectedDate) {
        console.log(idEtiqueta)
        fetchNoticias();
      }
    }, [token, idEtiqueta, selectedDate]);


    return (
      <div className="container mt-4">
        <div className="noticia-etiqueta-data-square">
          <div className="d-flex justify-content-between align-items-center">
            <h2>Notícias da Etiqueta e Data Selecionadas</h2>
            <Link to="/etiquetas" className="btn btn-success">
              Voltar
            </Link>
          </div>
          {noticias.items && noticias.items.length > 0 ? (
  noticias.items.map((noticia) => (
    <div key={noticia.titulo} className="card mb-3">
      <div className="card-body">
        <h2 className="card-title">{noticia.titulo}</h2>
        <p className="card-text">{noticia.introducao}</p>
        <p>Data de Publicação: {noticia.data_publicacao}</p>
        <p>Editorias: {noticia.editorias}</p>
      </div>
      <hr className="my-0" />
    </div>
  ))
) : (
  <p className="alert alert-info">Não há notícias na etiqueta e data selecionadas!</p>
)}
        </div>
      </div>
    );
  }


export default NoticiaEtiquetaData;