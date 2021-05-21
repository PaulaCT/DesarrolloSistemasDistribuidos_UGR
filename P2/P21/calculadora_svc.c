/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#include "calculadora.h"
#include <stdio.h>
#include <stdlib.h>
#include <rpc/pmap_clnt.h>
#include <string.h>
#include <memory.h>
#include <sys/socket.h>
#include <netinet/in.h>

#ifndef SIG_PF
#define SIG_PF void(*)(int)
#endif

static int *
_suma_1 (suma_1_argument *argp, struct svc_req *rqstp)
{
	return (suma_1_svc(argp->a, argp->b, rqstp));
}

static int *
_resta_1 (resta_1_argument *argp, struct svc_req *rqstp)
{
	return (resta_1_svc(argp->a, argp->b, rqstp));
}

static int *
_multi_1 (multi_1_argument *argp, struct svc_req *rqstp)
{
	return (multi_1_svc(argp->a, argp->b, rqstp));
}

static div_res *
_div_1 (div_1_argument *argp, struct svc_req *rqstp)
{
	return (div_1_svc(argp->a, argp->b, rqstp));
}

static char **
_suma_v_1 (char * *argp, struct svc_req *rqstp)
{
	return (suma_v_1_svc(*argp, rqstp));
}

static char **
_determ_1 (char * *argp, struct svc_req *rqstp)
{
	return (determ_1_svc(*argp, rqstp));
}

static void
calculadora_1(struct svc_req *rqstp, register SVCXPRT *transp)
{
	union {
		suma_1_argument suma_1_arg;
		resta_1_argument resta_1_arg;
		multi_1_argument multi_1_arg;
		div_1_argument div_1_arg;
	} argument;
	char *result;
	xdrproc_t _xdr_argument, _xdr_result;
	char *(*local)(char *, struct svc_req *);

	switch (rqstp->rq_proc) {
	case NULLPROC:
		(void) svc_sendreply (transp, (xdrproc_t) xdr_void, (char *)NULL);
		return;

	case SUMA:
		_xdr_argument = (xdrproc_t) xdr_suma_1_argument;
		_xdr_result = (xdrproc_t) xdr_int;
		local = (char *(*)(char *, struct svc_req *)) _suma_1;
		break;

	case RESTA:
		_xdr_argument = (xdrproc_t) xdr_resta_1_argument;
		_xdr_result = (xdrproc_t) xdr_int;
		local = (char *(*)(char *, struct svc_req *)) _resta_1;
		break;

	case MULTI:
		_xdr_argument = (xdrproc_t) xdr_multi_1_argument;
		_xdr_result = (xdrproc_t) xdr_int;
		local = (char *(*)(char *, struct svc_req *)) _multi_1;
		break;

	case DIV:
		_xdr_argument = (xdrproc_t) xdr_div_1_argument;
		_xdr_result = (xdrproc_t) xdr_div_res;
		local = (char *(*)(char *, struct svc_req *)) _div_1;
		break;

	default:
		svcerr_noproc (transp);
		return;
	}
	memset ((char *)&argument, 0, sizeof (argument));
	if (!svc_getargs (transp, (xdrproc_t) _xdr_argument, (caddr_t) &argument)) {
		svcerr_decode (transp);
		return;
	}
	result = (*local)((char *)&argument, rqstp);
	if (result != NULL && !svc_sendreply(transp, (xdrproc_t) _xdr_result, result)) {
		svcerr_systemerr (transp);
	}
	if (!svc_freeargs (transp, (xdrproc_t) _xdr_argument, (caddr_t) &argument)) {
		fprintf (stderr, "%s", "unable to free arguments");
		exit (1);
	}
	return;
}

static void
calculadora_compleja_1(struct svc_req *rqstp, register SVCXPRT *transp)
{
	union {
		char *suma_v_1_arg;
		char *determ_1_arg;
	} argument;
	char *result;
	xdrproc_t _xdr_argument, _xdr_result;
	char *(*local)(char *, struct svc_req *);

	switch (rqstp->rq_proc) {
	case NULLPROC:
		(void) svc_sendreply (transp, (xdrproc_t) xdr_void, (char *)NULL);
		return;

	case SUMA_V:
		_xdr_argument = (xdrproc_t) xdr_wrapstring;
		_xdr_result = (xdrproc_t) xdr_wrapstring;
		local = (char *(*)(char *, struct svc_req *)) _suma_v_1;
		break;

	case DETERM:
		_xdr_argument = (xdrproc_t) xdr_wrapstring;
		_xdr_result = (xdrproc_t) xdr_wrapstring;
		local = (char *(*)(char *, struct svc_req *)) _determ_1;
		break;

	default:
		svcerr_noproc (transp);
		return;
	}
	memset ((char *)&argument, 0, sizeof (argument));
	if (!svc_getargs (transp, (xdrproc_t) _xdr_argument, (caddr_t) &argument)) {
		svcerr_decode (transp);
		return;
	}
	result = (*local)((char *)&argument, rqstp);
	if (result != NULL && !svc_sendreply(transp, (xdrproc_t) _xdr_result, result)) {
		svcerr_systemerr (transp);
	}
	if (!svc_freeargs (transp, (xdrproc_t) _xdr_argument, (caddr_t) &argument)) {
		fprintf (stderr, "%s", "unable to free arguments");
		exit (1);
	}
	return;
}

int
main (int argc, char **argv)
{
	register SVCXPRT *transp;

	pmap_unset (CALCULADORA, CALCULADORA_V);
	pmap_unset (CALCULADORA_COMPLEJA, CALCULADORA_CV);

	transp = svcudp_create(RPC_ANYSOCK);
	if (transp == NULL) {
		fprintf (stderr, "%s", "cannot create udp service.");
		exit(1);
	}
	if (!svc_register(transp, CALCULADORA, CALCULADORA_V, calculadora_1, IPPROTO_UDP)) {
		fprintf (stderr, "%s", "unable to register (CALCULADORA, CALCULADORA_V, udp).");
		exit(1);
	}
	if (!svc_register(transp, CALCULADORA_COMPLEJA, CALCULADORA_CV, calculadora_compleja_1, IPPROTO_UDP)) {
		fprintf (stderr, "%s", "unable to register (CALCULADORA_COMPLEJA, CALCULADORA_CV, udp).");
		exit(1);
	}

	transp = svctcp_create(RPC_ANYSOCK, 0, 0);
	if (transp == NULL) {
		fprintf (stderr, "%s", "cannot create tcp service.");
		exit(1);
	}
	if (!svc_register(transp, CALCULADORA, CALCULADORA_V, calculadora_1, IPPROTO_TCP)) {
		fprintf (stderr, "%s", "unable to register (CALCULADORA, CALCULADORA_V, tcp).");
		exit(1);
	}
	if (!svc_register(transp, CALCULADORA_COMPLEJA, CALCULADORA_CV, calculadora_compleja_1, IPPROTO_TCP)) {
		fprintf (stderr, "%s", "unable to register (CALCULADORA_COMPLEJA, CALCULADORA_CV, tcp).");
		exit(1);
	}

	svc_run ();
	fprintf (stderr, "%s", "svc_run returned");
	exit (1);
	/* NOTREACHED */
}
