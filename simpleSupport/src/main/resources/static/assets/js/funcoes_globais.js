function exibirMensagemErro(elemento, mensagem) {
    elemento.textContent = mensagem;
    elemento.classList.remove('mensagem-escondida');
    elemento.classList.add('mensagem-visivel');

    setTimeout(function () {
        elemento.classList.remove('mensagem-visivel');
        elemento.classList.add('mensagem-escondida');
    }, 2500);
}

function formatCNPJ(cnpj) {
    cnpj = cnpj.replace(/\D/g, '');
    cnpj = cnpj.replace(/(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/, '$1.$2.$3/$4-$5');
    return cnpj;
}

function validarCNPJ(cnpj) {
    var regex = /^(\d{2})\.(\d{3})\.(\d{3})\/(\d{4})-(\d{2})$/;
    return regex.test(cnpj);
}

function paginacaoTabela(tabela) {
    $('#' + tabela).DataTable({
        "paging": true,
        "lengthMenu": [5, 10, 25],
        "searching": false,
        "ordering": true,
        "info": true,
        "language": {
            "lengthMenu": "_MENU_ registros por página",
            "zeroRecords": "Nenhum registro encontrado",
            "info": "Página _PAGE_ de _PAGES_",
            "infoEmpty": "Nenhum registro disponível",
            "infoFiltered": "(Filtrado de _MAX_ registros no total)",
            "paginate": {
                "previous": "Anterior",
                "next": "Próximo"
            }
        }
    });
}


