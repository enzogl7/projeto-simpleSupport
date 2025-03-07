$(document).ready(function () {
    paginacaoTabela('tabelaSistemas')
});

function modalNovoSistema() {
    $('#modalNovoSistema').modal('show');
}

function novoSistema() {
    var nomeSistema = document.getElementById('nomeSistema').value;
    var descricaoSistema = document.getElementById('descricaoSistema').value;
    var versaoSistema = document.getElementById('versaoSistema').value;
    var categoriaSistema = document.getElementById('categoriaSistema').value;
    var checkSistemaAtivo = document.getElementById('checkSistemaAtivo').checked;

    if (!nomeSistema || !descricaoSistema || !versaoSistema) {
        Swal.fire({
            title: "Ops!",
            text: "Preencha os campos obrigatórios!",
            icon: "warning",
            confirmButtonText: 'OK'
        })
        return;
    }

    $.ajax({
        url: '/empresa/salvarsistema',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            nome: nomeSistema,
            descricao: descricaoSistema,
            versao: versaoSistema,
            categoria: categoriaSistema,
            ativo: checkSistemaAtivo
        }),
        complete: function(xhr, status) {
            switch (xhr.status) {
                case 200:
                    Swal.fire({
                        title: "Pronto!",
                        text: "Sistema salvo com sucesso!",
                        icon: "success",
                        confirmButtonText: 'OK'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            location.reload();
                        }
                    });
                    break;
                case 500:
                    Swal.fire({
                        title: "Erro!",
                        text: "Ocorreu um erro ao salvar esse sistema.",
                        icon: "error"
                    });
                    break;
                default:
                    alert("Erro desconhecido: " + status);
            }
        }
    });
}
